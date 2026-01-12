package bank.cardissuing.transaction.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class AuthorizationRequest {
    private Long cardId;
    private BigDecimal amount;
    private String merchantName;
    private String merchantId;
}
