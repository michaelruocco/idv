package uk.co.idv.domain.usecases.identity.data;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequestMother;

import static org.assertj.core.api.Assertions.assertThat;

class MobileApplicationEligibleLoaderTest {

    private final MobileApplicationEligibleLoader loader = new MobileApplicationEligibleLoader();

    @Test
    void shouldReturnEligibleTrueIfAliasValueEndsInNine() {
        final Alias alias = AliasesMother.debitCardNumber("4929001111111119");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final boolean eligible = loader.load(request);

        assertThat(eligible).isTrue();
    }

    @Test
    void shouldReturnEligibleFalseIfAliasValueDoesNotEndInNine() {
        final Alias alias = AliasesMother.debitCardNumber("4929001111111111");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final boolean eligible = loader.load(request);

        assertThat(eligible).isFalse();
    }

}
