package com.haruse3kki.haruse3kki.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haruse3kki.haruse3kki.domain.User;
import com.haruse3kki.haruse3kki.dto.UserDTO;
import com.haruse3kki.haruse3kki.exception.CustomException;
import com.haruse3kki.haruse3kki.repository.UserRepository;
import com.haruse3kki.haruse3kki.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    //유저 확인 후, 토큰 발급
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String providerId=oAuth2User.getAttribute("id").toString();
        User user=userRepository.findByProviderId(providerId)
                .orElseThrow(()->new CustomException(404,"유저를 찾을 수 없습니다."));

        String accessToken= jwtUtil.generateAccessToken(user.getUserId());
        String refreshToken= jwtUtil.generateRefreshToken(user.getUserId());

        // 토큰을 쿼리 파라미터로 프론트에 전달
        String redirectUrl = "http://localhost:5173/callback" +
                "?accessToken=" + accessToken +
                "&refreshToken=" + refreshToken;

        System.out.println("리다이렉트 URL: " + redirectUrl);
        response.sendRedirect(redirectUrl);
    }
}
