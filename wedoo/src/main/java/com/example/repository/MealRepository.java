package com.example.repository;

import com.example.model.GiftCard;
import com.example.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal,Integer> {

    List<Meal> findMealByUserId(Integer userId) throws Exception;
}
