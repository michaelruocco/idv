package uk.co.idv.domain.entities.card.account;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.card.number.CardNumberMother;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {

    private static final AccountStatus STATUS = AccountStatus.CLOSED;
    private static final Collection<CardNumber> CARD_NUMBERS = CardNumberMother.oneCredit();

    @Test
    void shouldReturnStatus() {
        final Account account = new Account(STATUS, CARD_NUMBERS);

        assertThat(account.getStatus()).isEqualTo(STATUS);
    }

    @Test
    void shouldReturnCardNumbers() {
        final Account account = new Account(STATUS, CARD_NUMBERS);

        assertThat(account.getCardNumbers()).isEqualTo(CARD_NUMBERS);
    }

}
