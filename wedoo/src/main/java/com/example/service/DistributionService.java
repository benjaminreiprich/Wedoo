package com.example.service;

import com.example.dtos.DistributionDto;
import com.example.dtos.UserDto;
import com.example.exception.CustomException;
import com.example.exception.EmptyDistributionException;
import com.example.mapper.DistributionMapper;
import com.example.model.DistributionType;
import com.example.model.GiftCard;
import com.example.model.Meal;
import com.example.repository.GiftCardRepository;
import com.example.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class DistributionService {

    private final GiftCardRepository giftCardRepository;
    private final MealRepository mealRepository;
    private final DistributionMapper distributionMapper;

    public DistributionService(GiftCardRepository giftRepository, MealRepository mealRepository, DistributionMapper distributionMapper) {
        this.giftCardRepository = giftRepository;
        this.mealRepository = mealRepository;
        this.distributionMapper = distributionMapper;
    }

    public UserDto getGiftCardsByUserId (Integer userId) {
            List<GiftCard> giftCards = giftCardRepository.findGiftCardByUserId(userId);
            Integer balance = giftCards.stream()
                    .filter(giftCard -> LocalDate.now().isBefore(giftCard.getExpiredDate()))
                    .reduce(0, (amount, giftCard) -> amount + giftCard.getAmount(), Integer::sum);
            UserDto userToReturn = new UserDto();
            userToReturn.setId(userId);
            userToReturn.setBalance(balance);
            return userToReturn;
    }

    public Optional<DistributionDto> addGift(DistributionDto distributionDto) throws Exception {
        if (distributionDto == null) {
            throw new EmptyDistributionException();
        }
        try {
            if (distributionDto.getDistributionType().equals(DistributionType.GIFT)) {
                Optional<GiftCard> giftCard = distributionMapper.mapToGiftCard(distributionDto);
                if (giftCard.isPresent()) {
                    giftCard.get().setCreationDate(LocalDate.now());
                    giftCard.get().setExpiredDate(LocalDate.now().plusYears(1));
                } else {
                    throw new CustomException();
                }
                return distributionMapper.mapToDistributionFromGiftCard(giftCardRepository.save(giftCard.get()));
            }
            Optional<Meal> meal = distributionMapper.mapToMeal(distributionDto);
            if (meal.isPresent()) {
                meal.get().setCreationDate(LocalDate.now());
            } else {
                throw new CustomException();
            }
            return distributionMapper.mapToDistributionFromMeal(mealRepository.save(meal.get()));
        } catch (CustomException e) {
            throw new CustomException("Une erreur est survenue lors de l'ajout de l'objet");
        }
    }

    public UserDto getMealsByUserId (Integer userId) throws Exception {
        List<Meal> meals = mealRepository.findMealByUserId(userId);
        Integer balance = meals.stream()
                .filter(meal -> isBetweenDate(meal.getCreationDate()))
                .reduce(0, (amount, meal) -> amount + meal.getAmount(), Integer::sum);
        UserDto userToReturn = new UserDto();
        userToReturn.setId(userId);
        userToReturn.setBalance(balance);
        return userToReturn;
    }

    public Boolean isBetweenDate (LocalDate dateToCheck) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        LocalDate afterDate = LocalDate.now().isLeapYear() ?
            LocalDate.of(currentYear + 1, 2, 29)
        : LocalDate.of(currentYear + 1,2,28) ;
        boolean dd = dateToCheck.isBefore(afterDate);
        if(dateToCheck.getYear() == currentYear && dd || dateToCheck.getYear() == currentYear - 1 && dd) {
            return true;
        }
        return false;
    }
}
