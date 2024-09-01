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
    public ResponseEntity<List<PostResponseDTO>> getMyMeetings() {
        List<PostResponseDTO> meetings = participantService.getMyMeetings();
        return ResponseEntity.ok(meetings);
    }

    // 번개 참여 (Response List + Participants - 추가)
    @PostMapping("/{postId}")
    public ResponseEntity<Void> joinMeeting(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        participantService.joinMeeting(postId, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    // 번개 참여 취소 (Response OK)
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> cancelMeeting(@PathVariable Long postId) {
        participantService.cancelMeeting(postId);
        return ResponseEntity.noContent().build();
    }
}
