package zw.co.codehive.commons.util;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zw.co.codehive.commons.exceptions.BusinessValidationException;
import zw.co.codehive.transactiondesignpatterns.enums.AccountType;

import java.security.SecureRandom;
import java.util.Date;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountNumberGenerator {

    private static final String bankCbn = "51350";

    public static String generate(String userType) throws BusinessValidationException {
        String _6DigitCode = "9" + bankCbn;
        String nubanNumber;

        boolean accountExist;

        do {
            int min_val = 1;
            int max_val = 9999999;
            SecureRandom rand = new SecureRandom();
            rand.setSeed(new Date().getTime());

            int randomNum = rand.nextInt((max_val - min_val) + 1) + min_val;

            int remainingDigits = 9 - String.valueOf(randomNum).length() - 2;

            String nubanSerialNumber = getAccountTypeCode(userType) + "0".repeat(Math.max(0, remainingDigits)) + randomNum;

            String _16DigitCode = _6DigitCode + nubanSerialNumber;

            int[] bin = {3, 7, 3, 3, 7, 3, 3, 7, 3, 3, 7, 3, 3, 7, 3};
            String[] digits = _16DigitCode.split("");
            int sum = 0;

            for (int i = 0; i < digits.length; i++) {
                sum += Integer.parseInt(digits[i]) * bin[i];
            }

            System.out.println("sum " + sum);
            int modulo = sum % 10;

            int checkDigit = 10 - modulo;

            if (checkDigit == 10) {
                checkDigit = 0;
            }
            nubanNumber = nubanSerialNumber + checkDigit;
            log.info("### Nuban number " + nubanNumber);
            //TODO validate a new account number based on an existing account number

            accountExist = false; // Set correct value here
        } while (accountExist);

        return nubanNumber;
    }

    private static String getAccountTypeCode(String userType) throws BusinessValidationException {
        if (AccountType.COMPANY.name().equals(userType)) {
            return "10";
        }

        if (AccountType.INDIVIDUAL.name().equals(userType)) {
            return "20";
        }

        throw new BusinessValidationException("This account type's code could not be determined.");
    }
}
