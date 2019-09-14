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
        final IdvId idvId = new FakeIdvId();
        final Aliases aliases = Aliases.with(idvId);

        final Identity identity = new Identity(aliases);

        assertThat(identity.getIdvIdValue()).isEqualTo(idvId.getValueAsUuid());
    }

}
