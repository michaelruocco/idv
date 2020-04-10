package uk.co.idv.domain.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.usecases.identity.IdentityService.IdentityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityNotFoundExceptionTest {

    @Test
    void shouldReturnMessage() {
        final Alias alias = AliasesMother.creditCardNumber();

        final Throwable error = new IdentityNotFoundException(alias);

        assertThat(error.getMessage()).isEqualTo(toExpectedMessage(alias));
    }

    @Test
    void shouldReturnAlias() {
        final Alias alias = AliasesMother.creditCardNumber();

        final IdentityNotFoundException error = new IdentityNotFoundException(alias);

        assertThat(error.getAlias()).isEqualTo(alias);
    }

    private static String toExpectedMessage(final Alias alias) {
        return String.format("aliasType: %s aliasValue: %s",
                alias.getType(),
                alias.getValue());
    }

}
