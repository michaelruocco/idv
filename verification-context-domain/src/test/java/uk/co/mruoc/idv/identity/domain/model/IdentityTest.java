package uk.co.mruoc.idv.identity.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityTest {

    @Test
    void shouldReturnAliases() {
        final Aliases aliases = Aliases.empty();

        final Identity identity = new Identity(aliases);

        assertThat(identity.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnIdvIdAliasValue() {
        final IdvId idvId = AliasesMother.idvId();

        final Identity identity = new Identity(Aliases.with(idvId));

        assertThat(identity.getIdvIdValue()).isEqualTo(idvId.getValueAsUuid());
    }

    @Test
    void shouldReturnTrueIfHasAlias() {
        final IdvId idvId = AliasesMother.idvId();

        final Identity identity = new Identity(Aliases.with(idvId));

        assertThat(identity.hasAlias(idvId)).isTrue();
    }

    @Test
    void shouldReturnFalseIfDoesNotHaveAlias() {
        final IdvId idvId = AliasesMother.idvId();

        final Identity identity = new Identity(Aliases.with(idvId));

        final Alias creditCardNumber = AliasesMother.creditCardNumber();
        assertThat(identity.hasAlias(creditCardNumber)).isFalse();
    }

}
