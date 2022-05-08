package com.example.utils;

import com.example.dtos.CompanyDto;
import com.example.dtos.DistributionDto;
import com.example.dtos.UserDto;
import com.example.model.Company;
import com.example.model.DistributionType;
import com.example.model.GiftCard;
import com.example.model.Meal;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component("testDataBean")
public class TestData {

    public GiftCard giftCard = new GiftCard();
    public GiftCard giftCard2 = new GiftCard();
    public GiftCard giftCard3 = new GiftCard();
    public UserDto userDto = new UserDto();
    public UserDto userDto2 = new UserDto();
    public Meal meal = new Meal();
    public Meal meal2 = new Meal();
    public DistributionDto distributionDtoGift = new DistributionDto();
    public DistributionDto distributionDtoMeal = new DistributionDto();
    public CompanyDto companyDto = new CompanyDto();
    public Company company = new Company();


    {
        giftCard.setUserId(1);
        giftCard.setCompanyId(1);
        giftCard.setAmount(100);
        giftCard.setCreationDate(LocalDate.now());
        giftCard.setExpiredDate(LocalDate.now().plusYears(1));

        giftCard2.setUserId(2);
        giftCard2.setCompanyId(1);
        giftCard2.setAmount(100);
        giftCard2.setCreationDate(LocalDate.now());
        giftCard2.setExpiredDate(LocalDate.now().plusYears(1));

        giftCard3.setUserId(3);
        giftCard3.setCompanyId(1);
        giftCard3.setAmount(100);
        giftCard3.setCreationDate(LocalDate.of(2017,5,4));
        giftCard3.setExpiredDate(LocalDate.of(2018,5,4));

        userDto.setId(1);
        userDto.setBalance(100);

        userDto2.setId(1);
        userDto2.setBalance(200);

        meal.setId(1);
        meal.setCreationDate(LocalDate.now());
        meal.setAmount(100);
        meal.setCompanyId(1);
        meal.setUserId(1);

        meal2.setId(2);
        meal2.setCreationDate(LocalDate.of(2017,5,6));
        meal2.setAmount(100);
        meal2.setCompanyId(1);
        meal2.setUserId(1);

        distributionDtoGift.setAmount(100);
        distributionDtoGift.setUserId(1);
        distributionDtoGift.setCompanyId(1);
        distributionDtoGift.setDistributionType(DistributionType.GIFT);

        distributionDtoMeal.setAmount(100);
        distributionDtoMeal.setUserId(1);
        distributionDtoMeal.setCompanyId(1);
        distributionDtoMeal.setDistributionType(DistributionType.MEAL);

        companyDto.setId(1);
        companyDto.setBalance(1000);

        company.setId(1);
        companyDto.setBalance(1000);

    }
}
