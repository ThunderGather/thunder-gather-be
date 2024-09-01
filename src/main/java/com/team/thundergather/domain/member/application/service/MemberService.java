package com.team.thundergather.domain.member.application.service;
import com.team.thundergather.domain.member.application.dto.MemberCreateDTO;
import com.team.thundergather.domain.member.application.dto.MemberSigninDTO;
import com.team.thundergather.domain.member.dataAccess.entity.Member;
import com.team.thundergather.domain.member.dataAccess.enums.Role;
import com.team.thundergather.domain.member.dataAccess.enums.TokenName;
import com.team.thundergather.domain.member.dataAccess.repository.MemberRepository;
import com.team.thundergather.global.auth.jwt.component.JwtTokenProvider;
import com.team.thundergather.global.auth.jwt.dto.AccessTokenDto;
import com.team.thundergather.global.auth.jwt.dto.RefreshTokenDto;
import com.team.thundergather.global.auth.jwt.dto.TokenDto;
import com.team.thundergather.global.utils.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final StringRedisTemplate redisTemplate;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional
    public void signup(MemberCreateDTO dto, MultipartFile imagefile) {
        // 아이디 중복 체크
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }

        // 닉네임 중복 체크
        if (memberRepository.existsByNickname(dto.getNickname())) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        // 비밀번호 암호화
        dto.modifyPassword(passwordEncoder.encode(dto.getPassword()));
        Member member = Member.of(dto);

        // 이미지 파일 업데이트
        if (imagefile != null && !imagefile.isEmpty()) {
            String fileName = saveFileToDirectory(imagefile);
            member.addProfileImage(fileName);
        }

        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public AccessTokenDto signin(MemberSigninDTO dto, HttpServletResponse response) {
        // email로 Member 조회
        Member savedMember = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("로그인에 실패했습니다."));

        // Password 일치 여부 확인
        if (!passwordEncoder.matches(dto.getPassword(), savedMember.getPassword())) {
            throw new RuntimeException("로그인에 실패했습니다.");
        }

        // Authentication 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                savedMember.getId(), // User Id
                savedMember.getPassword(),
                Collections.singletonList(Role.ROLE_USER::name
            )
        );

        // 인증 정보를 기반으로 JWT Token 생성
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);
        RefreshTokenDto refreshTokenDto = tokenDto.refreshTokenDto();

        // Refresh Token을 Redis에 저장
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshTokenDto.token(), savedMember.getEmail(), refreshTokenDto.getExpiresInSecond(), TimeUnit.SECONDS);

        // Refresh Token을 쿠키에 담아서 전달
        Cookie cookie = CookieUtils.createCookie(TokenName.USER_REFRESH_TOKEN.name(),  refreshTokenDto.token(), refreshTokenDto.getExpiresInSecond());
        response.addCookie(cookie);

        return tokenDto.accessTokenDto();
    }

    // 파일을 로컬 디렉토리에 저장
    private String saveFileToDirectory(MultipartFile imagefile) {
        try {
            String fileName = UUID.randomUUID().toString() + "-" + imagefile.getOriginalFilename();
            Path imagefilePath = Paths.get(uploadDir + "/" + fileName);
            Files.createDirectories(imagefilePath.getParent());
            Files.write(imagefilePath, imagefile.getBytes());
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다.", e);
        }
    }
}
