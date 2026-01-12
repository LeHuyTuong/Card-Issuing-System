package bank.cardissuing.customer.domain;

import bank.cardissuing.customer.exception.InvalidStateTransitionException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KYCTest {

    @Test
    void verify_whenStatusPending_shouldTransitionToVerified() {
        KYC kyc = new KYC();
        kyc.setStatus(KYCStatus.PENDING);
        kyc.verify();

        assertEquals(KYCStatus.VERIFIED, kyc.getStatus());
        assertNotNull(kyc.getVerifiedAt());
    }

    @Test
    void verify_whenStatusVerified_shouldThrowException() {
        KYC kyc = new KYC();
        kyc.setStatus(KYCStatus.VERIFIED);
        assertThrows(InvalidStateTransitionException.class, () -> {
            kyc.verify();
        });
    }

    @Test
    void verify_whenStatusRejected_shouldThrowException() {
        KYC kyc = new KYC();
        kyc.setStatus(KYCStatus.REJECTED);
        assertThrows(InvalidStateTransitionException.class, () -> {
            kyc.verify();
        });
    }

    @Test
    void reject_whenStatusPending_shouldTransitionToRejected() {
        KYC kyc = new KYC();
        kyc.setStatus(KYCStatus.PENDING);
        kyc.reject();

        assertEquals(KYCStatus.REJECTED, kyc.getStatus());
    }

    @Test
    void reject_whenStatusVerified_shouldThrowException() {
        KYC kyc = new KYC();
        kyc.setStatus(KYCStatus.VERIFIED);
        assertThrows(InvalidStateTransitionException.class, () -> {
            kyc.reject();
        });
    }
}
