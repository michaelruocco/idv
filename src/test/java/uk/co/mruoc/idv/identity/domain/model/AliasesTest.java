package uk.co.mruoc.idv.identity.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Alias.AliasWithTypeNotFoundException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AliasesTest {

    @Test
    void shouldBeIterable() {
        final Alias idvId = new FakeIdvId();
        final Alias creditCardNumber = new FakeCreditCardNumber();

        final Aliases aliases = Aliases.with(idvId, creditCardNumber);

        assertThat(aliases).containsExactly(
                idvId,
                creditCardNumber
        );
    }

    @Test
    void shouldCreateEmptyInstance() {
        final Aliases aliases = Aliases.empty();

        assertThat(aliases).isEmpty();
    }

    @Test
    void shouldThrowExceptionIfIdvIdAliasNotPresent() {
        final Alias creditCardNumber = new FakeCreditCardNumber();
        final Aliases aliases = Aliases.with(creditCardNumber);

        final Throwable error = catchThrowable(aliases::getIdvIdValue);

        assertThat(error)
                .isInstanceOf(AliasWithTypeNotFoundException.class)
                .hasMessage(IdvId.TYPE);
    }

    @Test
    void shouldReturnIdvIdAliasValueIfPresent() {
        final IdvId idvId = new FakeIdvId();
        final Aliases aliases = Aliases.with(idvId);

        final UUID value = aliases.getIdvIdValue();

        assertThat(value).isEqualTo(idvId.getValueAsUuid());
    }

}
