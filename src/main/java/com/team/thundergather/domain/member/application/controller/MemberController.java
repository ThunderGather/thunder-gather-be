package com.team.thundergather.domain.member.application.controller;
import com.team.thundergather.domain.member.application.dto.MemberCreateDTO;
import com.team.thundergather.domain.member.application.dto.MemberSigninDTO;
import com.team.thundergather.domain.member.application.service.MemberService;
import com.team.thundergather.global.auth.jwt.dto.AccessTokenDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestPart("request") MemberCreateDTO dto,
                                         @RequestPart(name = "image", required = false) MultipartFile image) {
        memberService.signup(dto, image);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    /**
     * 로그인
     */
    @PostMapping("/signin")
    public ResponseEntity<AccessTokenDto> signin(@RequestBody MemberSigninDTO dto, HttpServletResponse response) {
        AccessTokenDto accessTokenDto = memberService.signin(dto, response);
        return ResponseEntity.status(HttpStatus.OK).body(accessTokenDto);
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        memberService.logout(request, response);
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 성공");
    }
}
