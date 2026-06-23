package com.haruse3kki.haruse3kki.controller;

import com.haruse3kki.haruse3kki.domain.User;
import com.haruse3kki.haruse3kki.service.SseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "SSE", description = "실시간 알림 API (Server-Sent Events)")
@RestController
@RequiredArgsConstructor
public class SseController {
    private final SseService sseService;

    @Operation(
            summary = "SSE 알림 구독",
            description = "서버에서 실시간 알림을 받기 위해 SSE 연결을 맺습니다. "
                    + "파트너가 식사를 기록하거나 댓글을 남길 때 이벤트가 전송됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SSE 연결 성공 (text/event-stream)")
    })
    @GetMapping(path = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@AuthenticationPrincipal User user) {
        return sseService.subscribe(user.getUserId());
    }
}
