package com.example.mapper;

import com.example.dtos.DistributionDto;
import com.example.model.DistributionType;
import com.example.model.GiftCard;
import com.example.model.Meal;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DistributionMapper {

    public Optional<Meal> mapToMeal (DistributionDto distributionDto) {
        if (distributionDto == null) {
            return Optional.empty();
        }

        Meal meal = new Meal();
        meal.setUserId(distributionDto.getUserId());
        meal.setCompanyId(distributionDto.getCompanyId());
        meal.setAmount(distributionDto.getAmount());

        return java.util.Optional.of(meal);
    }

    public Optional<GiftCard> mapToGiftCard (DistributionDto distributionDto) {
        if (distributionDto == null) {
            return Optional.empty();
        }

        GiftCard giftCard = new GiftCard();
        giftCard.setUserId(distributionDto.getUserId());
        giftCard.setCompanyId(distributionDto.getCompanyId());
        giftCard.setAmount(distributionDto.getAmount());

        return Optional.of(giftCard);
    }

    public Optional<DistributionDto> mapToDistributionFromMeal (Meal meal) {
        if (meal == null) {
            return Optional.empty();
        }

        DistributionDto distributionDto = new DistributionDto();
        distributionDto.setAmount(meal.getAmount());
        distributionDto.setCompanyId(meal.getCompanyId());
        distributionDto.setUserId(meal.getUserId());
        distributionDto.setDistributionType(DistributionType.MEAL);

        return Optional.of(distributionDto);
    }

    public Optional<DistributionDto> mapToDistributionFromGiftCard (GiftCard giftCard) {
        if (giftCard == null) {
            return Optional.empty();
        }

        DistributionDto distributionDto = new DistributionDto();
        distributionDto.setAmount(giftCard.getAmount());
        distributionDto.setCompanyId(giftCard.getCompanyId());
        distributionDto.setUserId(giftCard.getUserId());
        distributionDto.setDistributionType(DistributionType.GIFT);

        return Optional.of(distributionDto);
    }
}
