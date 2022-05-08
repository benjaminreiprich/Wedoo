package com.example.service;

import com.example.dtos.DistributionDto;
import com.example.dtos.UserDto;
import com.example.exception.CustomException;
import com.example.exception.EmptyDistributionException;
import com.example.mapper.DistributionMapper;
import com.example.model.GiftCard;
import com.example.model.Meal;
import com.example.repository.GiftCardRepository;
import com.example.repository.MealRepository;
import com.example.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class DistributionServiceTest {

    private DistributionService service;
    private GiftCardRepository repository;
    private MealRepository mealRepository;
    private DistributionMapper distributionMapper;

    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestData.class);
    private TestData testData = (TestData) context.getBean("testDataBean");

    @BeforeEach
    void setUp() {
        repository = mock(GiftCardRepository.class);
        mealRepository = mock(MealRepository.class);
        distributionMapper = mock(DistributionMapper.class);
        service = new DistributionService(repository,mealRepository,distributionMapper);
    }

    @Test
    void addNullGiftCard_should_throw_EmptyGiftCardException() throws EmptyDistributionException {

        assertThatThrownBy(() -> service.addGift(null))
                .isInstanceOf(EmptyDistributionException.class);
    }

    @Test
    void addGift_should_return_DistributionDto() throws Exception {
        when(repository.save(testData.giftCard)).thenReturn(testData.giftCard);
        when(distributionMapper.mapToDistributionFromGiftCard(testData.giftCard)).thenReturn(Optional.of(testData.distributionDtoGift));
        when(distributionMapper.mapToGiftCard(testData.distributionDtoGift)).thenReturn(Optional.of(testData.giftCard));

        DistributionDto result = service.addGift(testData.distributionDtoGift).get();

        assertThat(result).isEqualTo(testData.distributionDtoGift);
        verify(repository, times(1)).save(testData.giftCard);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void addMeal_should_return_DistributionDto() throws Exception {
        when(mealRepository.save(testData.meal)).thenReturn(testData.meal);
        when(distributionMapper.mapToDistributionFromMeal(testData.meal)).thenReturn(Optional.of(testData.distributionDtoMeal));
        when(distributionMapper.mapToMeal(testData.distributionDtoMeal)).thenReturn(Optional.of(testData.meal));

        DistributionDto result = service.addGift(testData.distributionDtoMeal).get();

        assertThat(result).isEqualTo(testData.distributionDtoMeal);
        verify(mealRepository, times(1)).save(testData.meal);
        verifyNoMoreInteractions(mealRepository);
    }


    @Test
    void addGift_should_return_CustomException() throws Exception {
        when(repository.save(testData.giftCard)).thenReturn(testData.giftCard);
        when(distributionMapper.mapToDistributionFromGiftCard(testData.giftCard)).thenReturn(Optional.of(testData.distributionDtoGift));
        when(distributionMapper.mapToGiftCard(testData.distributionDtoGift)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.addGift(testData.distributionDtoGift))
                .isInstanceOf(CustomException.class);    }

    @Test
    void addMeal_should_return_CustomException() throws Exception {
        when(mealRepository.save(testData.meal)).thenReturn(testData.meal);
        when(distributionMapper.mapToDistributionFromMeal(testData.meal)).thenReturn(Optional.of(testData.distributionDtoMeal));
        when(distributionMapper.mapToMeal(testData.distributionDtoMeal)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.addGift(testData.distributionDtoMeal))
                .isInstanceOf(CustomException.class);

    }

    @Test
    void getGiftCardsByUserId_should_return_user_dto() {
        List<GiftCard> giftcards = List.of(testData.giftCard,testData.giftCard2, testData.giftCard3);
        when(repository.findGiftCardByUserId(1)).thenReturn(giftcards);

        UserDto userDto = service.getGiftCardsByUserId(1);

        assertThat(userDto).isEqualTo(testData.userDto2);
    }



    @Test
    void getMealsByUserId_should_return_user_dto() throws Exception{
        List<Meal> meals = List.of(testData.meal,testData.meal2);
        when(mealRepository.findMealByUserId(1)).thenReturn(meals);

        UserDto userDto = service.getMealsByUserId(1);

        assertThat(userDto).isEqualTo(testData.userDto);
    }


    @Test
    void isBetweenDate_should_return_true(){
        boolean b = service.isBetweenDate(LocalDate.now());
        assertThat(b).isEqualTo(true);
    }

    @Test
    void isBetweenDate_should_return_false(){
        boolean b = service.isBetweenDate(LocalDate.of(2020,5,3));
        assertThat(b).isEqualTo(false);
    }

}
