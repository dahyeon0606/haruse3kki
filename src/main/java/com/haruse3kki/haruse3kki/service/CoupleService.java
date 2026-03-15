package com.haruse3kki.haruse3kki.service;

import com.haruse3kki.haruse3kki.domain.Couple;
import com.haruse3kki.haruse3kki.domain.User;
import com.haruse3kki.haruse3kki.dto.CoupleDTO;
import com.haruse3kki.haruse3kki.exception.CustomException;
import com.haruse3kki.haruse3kki.repository.CoupleRepository;
import com.haruse3kki.haruse3kki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CoupleService {
    private final CoupleRepository coupleRepository;
    private final RedisTemplate<String,String> redisTemplate;
    private final UserRepository userRepository;

    public String generateInviteCode(Long userId){
        String code = UUID.randomUUID().toString();
        redisTemplate.opsForValue()
                .set("invite:"+code,userId.toString(),30, TimeUnit.MINUTES);
        return code;
    }

    public void acceptInviteCode(Long userId,String code){
        String redisUserId=redisTemplate.opsForValue().get("invite:"+code);
        if(redisUserId==null){
            throw new CustomException(400,"유효하지 않은 초대 코드");
        }
        if(redisUserId.equals(userId.toString())){
            throw new CustomException(400,"자기 자신과 커플 불가");
        }

        User user1=userRepository.findById(userId).orElseThrow(()->new CustomException(404,"유저를 찾을 수 없습니다."));
        User user2 = userRepository.findById(Long.parseLong(redisUserId)).orElseThrow(()->new CustomException(404,"유저를 찾을 수 없습니다."));

        coupleRepository.save(
                Couple.builder()
                        .user1(user1)
                        .user2(user2)
                .build()
        );
        redisTemplate.delete("invite:"+code);
    }

    public void deleteCouple(Long userId, Long coupleId){
        Couple couple = coupleRepository.findById(coupleId)
                .orElseThrow(() -> new CustomException(404, "커플이 아닙니다."));

        // 내 커플이 맞는지 검증
        if(!couple.getUser1().getUserId().equals(userId) &&
                !couple.getUser2().getUserId().equals(userId)){
            throw new CustomException(403, "권한이 없습니다.");
        }

        coupleRepository.delete(couple);
    }

    public List<CoupleDTO.GetCoupleResponse> getCouples(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(404, "유저를 찾을 수 없습니다."));

        List<Couple> couples = coupleRepository.findByUser1OrUser2(user, user);

        return couples.stream()
                .map(couple -> {
                    // 상대방 찾기 (내가 user1이면 user2가 상대방, 반대도 마찬가지)
                    User partner = couple.getUser1().getUserId().equals(userId)
                            ? couple.getUser2()
                            : couple.getUser1();

                    return new CoupleDTO.GetCoupleResponse(
                            couple.getCoupleId(),
                            partner.getNickname(),
                            couple.getCreatedAt()
                    );
                })
                .toList();
    }
}
