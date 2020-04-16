package uk.co.idv.domain.entities.identity.alias;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias.AliasWithTypeNotFoundException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AliasesTest {

    @Test
    void shouldBeIterable() {
        final Alias idvId = AliasesMother.idvId();
        final Alias creditCardNumber = AliasesMother.creditCardNumber();

        final Aliases aliases = Aliases.with(idvId, creditCardNumber);

        assertThat(aliases).containsExactly(
                idvId,
                creditCardNumber
        );
    }

    @Test
    void shouldReturnStream() {
        final Alias idvId = AliasesMother.idvId();
        final Alias creditCardNumber = AliasesMother.creditCardNumber();

        final Aliases aliases = Aliases.with(idvId, creditCardNumber);

        assertThat(aliases.stream()).containsExactly(
                idvId,
                creditCardNumber
        );
    }

    @Test
    void shouldReturnSize() {
        final Alias idvId = AliasesMother.idvId();
        final Alias creditCardNumber = AliasesMother.creditCardNumber();

        final Aliases aliases = Aliases.with(idvId, creditCardNumber);

        assertThat(aliases.size()).isEqualTo(2);
    }

    @Test
    void shouldCreateEmptyInstance() {
        final Aliases aliases = Aliases.empty();

        assertThat(aliases).isEmpty();
    }

    @Test
    void shouldThrowExceptionIfIdvIdAliasNotPresent() {
        final Alias creditCardNumber = AliasesMother.creditCardNumber();
        final Aliases aliases = Aliases.with(creditCardNumber);

        final Throwable error = catchThrowable(aliases::getIdvIdValue);

        assertThat(error)
                .isInstanceOf(AliasWithTypeNotFoundException.class)
                .hasMessage(IdvId.TYPE);
    }

    @Test
    void shouldReturnIdvIdAliasValueIfPresent() {
        final IdvId idvId = AliasesMother.idvId();
        final Aliases aliases = Aliases.with(idvId);

        final UUID value = aliases.getIdvIdValue();

        assertThat(value).isEqualTo(idvId.getValueAsUuid());
    }

    @Test
    void shouldReturnTrueIfContainsAlias() {
        final Alias idvId = AliasesMother.idvId();

        final Aliases aliases = Aliases.with(idvId);

        assertThat(aliases.contains(idvId)).isTrue();
    }

    @Test
    void shouldReturnFalseIfDoesNotContainAlias() {
        final Alias idvId = AliasesMother.idvId();

        final Aliases aliases = Aliases.with(idvId);

        final Alias creditCardNumber = AliasesMother.creditCardNumber();
        assertThat(aliases.contains(creditCardNumber)).isFalse();
    }

    @Test
    void shouldAddAlias() {
        final Alias idvId = AliasesMother.idvId();
        final Aliases aliases = Aliases.with(idvId);
        final Alias creditCardNumber = AliasesMother.creditCardNumber();

        final Aliases addedAliases = aliases.add(creditCardNumber);

        assertThat(addedAliases).containsExactly(idvId, creditCardNumber);
    }

}
