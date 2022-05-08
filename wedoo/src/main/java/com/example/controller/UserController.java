package com.example.controller;

import com.example.dtos.UserDto;
import com.example.service.DistributionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private DistributionService distributionService;

    public UserController(DistributionService distributionService) {
        this.distributionService = distributionService;
    }

    @GetMapping("/gift/balance/{userId}")
    public ResponseEntity<?> getGiftBalanceByUserId(@PathVariable Integer userId) {
        try {
            UserDto userToReturn = distributionService.getGiftCardsByUserId(userId);
            return ResponseEntity.ok().body(userToReturn);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/meal/balance/{userId}")
    public ResponseEntity<?> getMealBalanceByUserId(@PathVariable Integer userId) {
        try {
            UserDto userToReturn = distributionService.getMealsByUserId(userId);
            return ResponseEntity.ok().body(userToReturn);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
