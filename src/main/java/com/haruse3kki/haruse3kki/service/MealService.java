package com.haruse3kki.haruse3kki.service;

import com.haruse3kki.haruse3kki.domain.Couple;
import com.haruse3kki.haruse3kki.domain.Meal;
import com.haruse3kki.haruse3kki.domain.User;
import com.haruse3kki.haruse3kki.dto.MealDTO;
import com.haruse3kki.haruse3kki.enums.MealType;
import com.haruse3kki.haruse3kki.exception.CustomException;
import com.haruse3kki.haruse3kki.repository.CoupleRepository;
import com.haruse3kki.haruse3kki.repository.MealRepository;
import com.haruse3kki.haruse3kki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private final CoupleRepository coupleRepository;

    public void uploadMeal(Long userId, MealDTO.UploadMealRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(404, "유저를 찾을 수 없습니다."));

        mealRepository.findByMealTypeAndDateAndUser(request.getMealType(), request.getDate(), user)
                .ifPresent(m -> { throw new CustomException(400, "이미 기록했습니다."); });

        mealRepository.save(
                Meal.builder()
                        .user(user)
                        .content(request.getContent())
                        .mealType(request.getMealType())
                        .photoUrl(request.getPhotoUrl())
                        .eatenAt(request.getEatenAt())
                        .date(request.getDate())
                .build());
    }

    public MealDTO.DailyResponse viewMeals(Long userId, Long coupleId, LocalDate date) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(404, "유저를 찾을 수 없습니다."));
        Couple couple = coupleRepository.findByCoupleId(coupleId);
        User partner = couple.getUser1().getUserId().equals(user.getUserId())
                ? couple.getUser2()
                : couple.getUser1();

        List<MealDTO.DailyMealView> dailyMealViews = new ArrayList<>();

        for(MealType mealType : MealType.values()) {
            //내 식사 기록
            Optional<Meal> myMeal = mealRepository.findByMealTypeAndDateAndUser(mealType, date, user);
            MealDTO.MealInfo myMealInfo = myMeal
                    .map(m-> new MealDTO.MealInfo(m.getMealId(),true,m.getContent(),m.getPhotoUrl(),m.getEatenAt()))
                    .orElse(null);

            //파트너 식사 기록
            MealDTO.MealInfo partnerMealInfo = mealRepository
                    .findByMealTypeAndDateAndUser(mealType, date, partner)
                    .map(m -> {
                        boolean canSeeContent = myMeal.isPresent();
                        return new MealDTO.MealInfo(
                                m.getMealId(),
                                true,
                                canSeeContent ? m.getContent() : null,   // 내가 기록했을 때만 내용 공개
                                canSeeContent ? m.getPhotoUrl() : null,
                                canSeeContent ? m.getEatenAt() : null
                        );
                    })
                    .orElse(null);

            dailyMealViews.add(new MealDTO.DailyMealView(mealType,myMealInfo,partnerMealInfo));
        }

        return new MealDTO.DailyResponse(date,dailyMealViews);

    }
}
