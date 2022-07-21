//package zw.co.jugaad.transactiondesignpatterns.factory;
//
//import zw.co.jugaad.transactiondesignpatterns.dto.account.CompanyAccountRequestDto;
//import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Account;
//import zw.co.jugaad.transactiondesignpatterns.model.fudzo.CompanyAccount;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//
//public class CompanyAccountImpl {
//
//    public Account createAccount(CompanyAccountRequestDto companyAccountRequestDto) {
//        CompanyAccount account = new CompanyAccount();
//        account.setAccountNumber(UUID.randomUUID().toString());
//        account.setBalance(BigDecimal.ZERO);
//        account.setCompanyName(companyAccountRequestDto.getCompanyName());
//        account.setRegistrationNumber(companyAccountRequestDto.getRegistrationNumber());
//        account.setContactNumber(companyAccountRequestDto.getContactNumber());
//        account.setEmail(companyAccountRequestDto.getEmail());
//        return account;
//    }
//}