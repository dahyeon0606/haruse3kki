package com.haruse3kki.haruse3kki.controller;

import com.haruse3kki.haruse3kki.domain.Couple;
import com.haruse3kki.haruse3kki.domain.User;
import com.haruse3kki.haruse3kki.dto.CoupleDTO;
import com.haruse3kki.haruse3kki.dto.UserDTO;
import com.haruse3kki.haruse3kki.service.CoupleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CoupleController {
    private final CoupleService coupleService;

    @PostMapping("/couples/invite")
    public ResponseEntity<String> invite(@AuthenticationPrincipal User user){
        String code = coupleService.generateInviteCode(user.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(code);
    }

    @PostMapping("/couples/accept")
    public ResponseEntity<String> accept(@AuthenticationPrincipal User user, @RequestBody UserDTO.InviteCodeRequest request){
        coupleService.acceptInviteCode(user.getUserId(),request.getCode());
        return ResponseEntity.status(HttpStatus.OK).body("커플 생성 완료");
    }

    @GetMapping("/couples")
    public ResponseEntity<List<CoupleDTO.GetCoupleResponse>> getCouples(@AuthenticationPrincipal User user){
        List<CoupleDTO.GetCoupleResponse> couples = coupleService.getCouples(user.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(couples);
    }

    @DeleteMapping("/couples/{coupleId}")
    public ResponseEntity<String> delete(@AuthenticationPrincipal User user,@PathVariable Long coupleId){
        coupleService.deleteCouple(user.getUserId(),coupleId);
        return ResponseEntity.status(HttpStatus.OK).body("커플 해제 완료");
    }
}
