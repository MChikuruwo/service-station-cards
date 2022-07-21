package zw.co.jugaad.fudzocommons.util;

import org.apache.commons.lang3.RandomStringUtils;

public class OtherUtils {

    private OtherUtils() {
    }

    public static String generateReferenceNumber() {
        return ("FTR").concat(RandomStringUtils.randomNumeric(5)).concat("THL");
    }

    public static String generateUserId(){
        return ("THL").concat(RandomStringUtils.randomNumeric(3));
    }


    public static String generateTerminalId(){
        return ("THLT").concat(RandomStringUtils.randomNumeric(4));
    }


    public static String generateCardNumber(){
        return ("9301").concat("8091").concat(RandomStringUtils.randomNumeric(8));
    }

    public static String generateRegistrationNumber(){
        return ("THLR").concat(RandomStringUtils.randomNumeric(4));
    }

    public static String generateAttendantId(){
        return ("THLA").concat(RandomStringUtils.randomNumeric(4));
    }

    public static String generateOwnerId(){
        return ("THLO").concat(RandomStringUtils.randomNumeric(4));
    }

}