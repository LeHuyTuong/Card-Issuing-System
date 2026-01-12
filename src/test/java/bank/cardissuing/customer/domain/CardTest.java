package bank.cardissuing.customer.domain;

import bank.cardissuing.card.domain.Card;
import bank.cardissuing.card.domain.CardStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    private Card newCardWithStatus(CardStatus status) {
        Card c = new Card();
        c.setStatus(status);
        return c;
    }

    @Test
    void activate_whenStatusCreated_shouldTransitionToActive(){
        Card c = newCardWithStatus(CardStatus.CREATED);
        c.activate();
        assertEquals(CardStatus.ACTIVE, c.getStatus());
    }

    @Test
    void activate_whenStatusActive_shouldThrowException(){
        Card c = newCardWithStatus(CardStatus.ACTIVE);
        assertThrows(IllegalStateException.class, c::activate);
    }

    @Test
    void suspend_whenStatusActive_shouldTransitionToSuspended(){
        Card c = newCardWithStatus(CardStatus.ACTIVE);
        c.suspend();
        assertEquals(CardStatus.SUSPENDED, c.getStatus());
    }

    @Test
    void suspend_whenStatusCreated_shouldThrowException(){
        Card c = newCardWithStatus(CardStatus.CREATED);
        assertThrows(IllegalStateException.class, c::suspend);
    }

    @Test
    void block_whenStatusActive_shouldTransitionToBlocked(){
        Card c = newCardWithStatus(CardStatus.ACTIVE);
        c.block();
        assertEquals(CardStatus.BLOCKED, c.getStatus());
    }

    @Test
    void block_whenStatusSuspended_shouldTransitionToBlocked(){
        Card c = newCardWithStatus(CardStatus.SUSPENDED);
        c.block();
        assertEquals(CardStatus.BLOCKED, c.getStatus());
    }

    @Test
    void block_whenStatusCreated_shouldThrowException(){
        Card c = newCardWithStatus(CardStatus.CREATED);
        assertThrows(IllegalStateException.class, c::block);
    }

    @Test
    void close_whenStatusBlocked_shouldTransitionToClosed(){
        Card c = newCardWithStatus(CardStatus.BLOCKED);
        c.close();
        assertEquals(CardStatus.CLOSED, c.getStatus());
    }

    @Test
    void close_whenStatusSuspended_shouldTransitionToClosed(){
        Card c = newCardWithStatus(CardStatus.SUSPENDED);
        c.close();
        assertEquals(CardStatus.CLOSED, c.getStatus());
    }

    @Test
    void close_whenStatusCreated_shouldThrowException(){
        Card c = newCardWithStatus(CardStatus.CREATED);
        assertThrows(IllegalStateException.class, c::close);
    }

    @Test
    void validateForAuthorization_whenActive_shouldPass(){
        Card c = newCardWithStatus(CardStatus.ACTIVE);
        c.setExpiryDate(java.time.LocalDate.now().plusDays(1));
        assertDoesNotThrow(c::validateForAuthorization);
    }

    @Test
    void validateForAuthorization_whenNotActive_shouldThrowException() {
        Card c = newCardWithStatus(CardStatus.SUSPENDED);
        c.setExpiryDate(java.time.LocalDate.now().plusDays(1));
        assertThrows(IllegalStateException.class, c::validateForAuthorization);
    }

    @Test
    void validateForAuthorization_whenExpired_shouldThrowException() {
        Card c = newCardWithStatus(CardStatus.ACTIVE);
        c.setExpiryDate(java.time.LocalDate.now().minusDays(1));
        assertThrows(IllegalStateException.class, c::validateForAuthorization);
    }
}
