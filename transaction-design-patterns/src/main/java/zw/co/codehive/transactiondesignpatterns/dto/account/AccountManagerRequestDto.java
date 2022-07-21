package zw.co.codehive.transactiondesignpatterns.dto.account;


import lombok.Data;

@Data
public class AccountManagerRequestDto {
    private String companyAccount;

    private String companyName;
}