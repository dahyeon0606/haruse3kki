package com.haruse3kki.haruse3kki.service;

import com.haruse3kki.haruse3kki.domain.User;
import com.haruse3kki.haruse3kki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    //유저 정보 DB에 저장 및 조회
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String providerId=oAuth2User.getAttribute("id").toString();
        Map<String,Object> properties=oAuth2User.getAttribute("properties");
        String name=(String)properties.get("nickname");

        userRepository.findByProviderId(providerId) //providerId가 DB에 없을 경우만 저장
                .orElseGet(()->userRepository.save(
                        User.builder()
                                .nickname(name)
                                .provider("kakao")
                                .providerId(providerId)
                                .build()
                ));
        return oAuth2User;
    }
}
