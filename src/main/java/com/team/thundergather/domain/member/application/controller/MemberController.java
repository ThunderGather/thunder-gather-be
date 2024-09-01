package com.team.thundergather.domain.member.application.controller;
import com.team.thundergather.domain.member.application.dto.MemberCreateDTO;
import com.team.thundergather.domain.member.application.service.MemberService;
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

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestPart("request") MemberCreateDTO dto,
                                         @RequestPart(name = "image", required = false) MultipartFile image) {
        memberService.signup(dto, image);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }
}
