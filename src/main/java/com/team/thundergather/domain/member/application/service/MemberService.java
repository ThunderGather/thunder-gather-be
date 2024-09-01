package com.team.thundergather.domain.member.application.service;

import com.team.thundergather.domain.member.application.dto.MemberCreateDTO;
import com.team.thundergather.domain.member.dataAccess.entity.Member;
import com.team.thundergather.domain.member.dataAccess.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

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
