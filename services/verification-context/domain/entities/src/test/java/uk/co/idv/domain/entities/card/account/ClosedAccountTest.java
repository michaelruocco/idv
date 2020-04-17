package uk.co.idv.domain.entities.card.account;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.card.number.CardNumberMother;

import static org.assertj.core.api.Assertions.assertThat;

class ClosedAccountTest {

    private static final CardNumber CARD_NUMBER = CardNumberMother.credit();

    @Test
    void shouldReturnStatus() {
        final Account account = new ClosedAccount(CARD_NUMBER);

        assertThat(account.getStatus()).isEqualTo(ClosedAccount.STATUS);
    }

    @Test
    void shouldReturnCardNumbers() {
        final Account account = new ClosedAccount(CARD_NUMBER);

        assertThat(account.getCardNumbers()).containsExactly(CARD_NUMBER);
    }

}
