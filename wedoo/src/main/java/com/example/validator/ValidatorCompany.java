package com.example.validator;

import com.example.dtos.DistributionDto;
import com.example.exception.CustomException;

public class ValidatorCompany {

    public static void validateCompany(DistributionDto distributionDto, Integer companyAmount) throws CustomException {
        validateCompanyAmount(companyAmount,distributionDto.getAmount());
    }

    public static  void validateCompanyAmount (Integer companyAmount, Integer giftCardAmount) throws CustomException {
        if (companyAmount <= 0 || giftCardAmount > companyAmount ) {
            throw new CustomException("La compagnie ne dispose plus des fonds nécéssaires");
        }
    }
}
