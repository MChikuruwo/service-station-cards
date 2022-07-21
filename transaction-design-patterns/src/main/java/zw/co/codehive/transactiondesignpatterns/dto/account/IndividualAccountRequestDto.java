package zw.co.codehive.transactiondesignpatterns.dto.account;

import lombok.Data;

@Data
public class IndividualAccountRequestDto extends AccountRequestDto {
    private String idNumber;
}
