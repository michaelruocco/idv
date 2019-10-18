package uk.co.mruoc.idv.identity.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;
import uk.co.mruoc.idv.identity.domain.service.IdentityService.IdentityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityNotFoundExceptionTest {

    @Test
    void shouldReturnMessage() {
        final Alias alias = new FakeCreditCardNumber();

        final Throwable error = new IdentityNotFoundException(alias);

        assertThat(error.getMessage()).isEqualTo(toExpectedMessage(alias));
    }

    @Test
    void shouldReturnAlias() {
        final Alias alias = new FakeCreditCardNumber();

        final IdentityNotFoundException error = new IdentityNotFoundException(alias);

        assertThat(error.getAlias()).isEqualTo(alias);
    }

    private static String toExpectedMessage(final Alias alias) {
        return String.format("aliasType: %s aliasValue: %s",
                alias.getType(),
                alias.getValue());
    }

}
