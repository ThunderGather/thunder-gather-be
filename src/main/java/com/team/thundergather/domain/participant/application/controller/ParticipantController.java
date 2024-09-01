package com.team.thundergather.domain.participant.application.controller;

import com.team.thundergather.domain.participant.application.service.ParticipantService;
import com.team.thundergather.domain.post.applicaion.dto.PostResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meeting")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    // 내가 참여한 번개 목록 조회 (Response List + Participants)
    @GetMapping("/mymeeting")
    public ResponseEntity<List<PostResponseDTO>> getMyMeetings(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.valueOf(userDetails.getUsername()); // User Id
        List<PostResponseDTO> meetings = participantService.getMyMeetings(memberId);
        return ResponseEntity.ok(meetings);
    }

    // 번개 참여 (Response OK)
    @PostMapping("/{postId}")
    public ResponseEntity<Void> joinMeeting(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.valueOf(userDetails.getUsername()); // User Id
        participantService.joinMeeting(postId, memberId);
        return ResponseEntity.ok().build();
    }

    // 번개 참여 취소 (Response NO_CONTENT)
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> cancelMeeting(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.valueOf(userDetails.getUsername()); // User Id
        participantService.cancelMeeting(postId, memberId);
        return ResponseEntity.noContent().build();
    }
}
