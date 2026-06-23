package com.haruse3kki.haruse3kki.controller;

import com.haruse3kki.haruse3kki.domain.User;
import com.haruse3kki.haruse3kki.dto.CoupleDTO;
import com.haruse3kki.haruse3kki.dto.UserDTO;
import com.haruse3kki.haruse3kki.service.CoupleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Couple", description = "커플 API")
@RestController
@RequiredArgsConstructor
public class CoupleController {
    private final CoupleService coupleService;

    @Operation(summary = "커플 초대 코드 생성", description = "상대방에게 전달할 커플 초대 코드를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "초대 코드 생성 성공"),
            @ApiResponse(responseCode = "400", description = "이미 커플인 경우 등 잘못된 요청")
    })
    @PostMapping("/couples/invite")
    public ResponseEntity<String> invite(@AuthenticationPrincipal User user) {
        String code = coupleService.generateInviteCode(user.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(code);
    }

    @Operation(summary = "커플 초대 코드 수락", description = "받은 초대 코드를 입력해 커플을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "커플 생성 완료"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 초대 코드")
    })
    @PostMapping("/couples/accept")
    public ResponseEntity<String> accept(
            @AuthenticationPrincipal User user,
            @RequestBody UserDTO.InviteCodeRequest request) {
        coupleService.acceptInviteCode(user.getUserId(), request.getCode());
        return ResponseEntity.status(HttpStatus.OK).body("커플 생성 완료");
    }

    @Operation(summary = "내 커플 목록 조회", description = "현재 사용자의 커플 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/couples")
    public ResponseEntity<List<CoupleDTO.GetCoupleResponse>> getCouples(@AuthenticationPrincipal User user) {
        List<CoupleDTO.GetCoupleResponse> couples = coupleService.getCouples(user.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(couples);
    }

    @Operation(summary = "커플 해제", description = "특정 커플 관계를 해제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "커플 해제 완료"),
            @ApiResponse(responseCode = "403", description = "본인의 커플이 아님"),
            @ApiResponse(responseCode = "404", description = "커플을 찾을 수 없음")
    })
    @DeleteMapping("/couples/{coupleId}")
    public ResponseEntity<String> delete(
            @AuthenticationPrincipal User user,
            @Parameter(description = "커플 ID", required = true) @PathVariable Long coupleId) {
        coupleService.deleteCouple(user.getUserId(), coupleId);
        return ResponseEntity.status(HttpStatus.OK).body("커플 해제 완료");
    }
}
