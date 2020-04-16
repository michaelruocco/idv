package uk.co.idv.domain.usecases.identity.data;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.identity.alias.CreditCardNumber;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequestMother;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasLoaderTest {

    private final AliasLoader loader = new AliasLoader();

    @Test
    void shouldOnlyReturnProvidedAliasIfProvidedAliasValueDoesNotEndInTwo() {
        final Alias alias = AliasesMother.creditCardNumber("4929001111111111");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final Aliases aliases = loader.load(request);

        assertThat(aliases).containsExactly(alias);
    }

    @Test
    void shouldReturnAdditionalAliasWithIncrementedValueIfProvidedAliasValueEndsInTwo() {
        final Alias alias = AliasesMother.creditCardNumber("4929001111111112");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final Aliases aliases = loader.load(request);

        assertThat(aliases).containsExactly(
                alias,
                new CreditCardNumber("4929001111111113")
        );
    }

}
