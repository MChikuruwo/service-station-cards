//package zw.co.jugaad.transactiondesignpatterns.factory;
//
//import zw.co.jugaad.transactiondesignpatterns.dto.account.IndividualAccountRequestDto;
//import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Account;
//import zw.co.jugaad.transactiondesignpatterns.model.fudzo.IndividualAccount;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//
//
//
//public class IndividualAccountImpl {
//
//    public Account createAccount(IndividualAccountRequestDto individualAccountRequestDto) {
//        IndividualAccount account = new IndividualAccount();
//        account.setAccountNumber(UUID.randomUUID().toString());
//        account.setEmail(individualAccountRequestDto.getEmail());
//        account.setMobile(individualAccountRequestDto.getMobile());
//        account.setFirstname(individualAccountRequestDto.getFirstname());
//        account.setLastname(individualAccountRequestDto.getLastname());
//        account.setIdNumber(individualAccountRequestDto.getIdNumber());
//        account.setBalance(BigDecimal.ZERO);
//        return account;
//    }
//
//
//}
