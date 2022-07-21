package zw.co.jugaad.transactiondesignpatterns.dto.account;

import lombok.Data;
import zw.co.jugaad.transactiondesignpatterns.dto.account.AccountRequestDto;

import javax.validation.constraints.Email;

@Data
public class IndividualAccountRequestDto extends AccountRequestDto {
    private String idNumber;
}
