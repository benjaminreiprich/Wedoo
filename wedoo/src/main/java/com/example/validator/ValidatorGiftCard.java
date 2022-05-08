package com.example.validator;

import com.example.dtos.DistributionDto;
import com.example.exception.CustomException;

public class ValidatorGiftCard {

    public ValidatorGiftCard() {}

    public static void validateGiftCard(DistributionDto distributionDto) throws CustomException {
        validateUserId(distributionDto.getUserId());
        validateCompanyId(distributionDto.getCompanyId());
        validateAmount(distributionDto.getAmount());
    }

    public static void validateUserId(Integer userId) throws CustomException {
        if (userId== null || userId<= 0) {
            throw new CustomException("le user Id renseigné est incorrect");
        }
    }

    public static void validateCompanyId(Integer companyId) throws CustomException {
        if (companyId== null || companyId<= 0) {
            throw new CustomException("le company Id renseigné est incorrect");
        }
    }

   public static void validateAmount (Integer amount) throws CustomException {
        if (amount <= 0 ) {
            throw new CustomException("Le montant donné ne peut pas être nul");
        }
   }
}
