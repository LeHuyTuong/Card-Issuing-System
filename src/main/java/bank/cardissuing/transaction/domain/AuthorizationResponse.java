package bank.cardissuing.transaction.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationResponse {
    private boolean approved;
    private String responseCode;
    private String approvalCode;
    private BigDecimal amount;
    private String message;

    public static AuthorizationResponse decline(String reason) {
        return AuthorizationResponse.builder()
                .approved(false)
                .responseCode("51")
                .approvalCode(null)
                .amount(null)
                .message(reason)
                .build();
    }

    public static AuthorizationResponse approve(String authCode, BigDecimal balance) {
        return AuthorizationResponse.builder()
                .approved(true)
                .responseCode("00")
                .approvalCode(authCode)
                .amount(balance)
                .build();
    }
}
