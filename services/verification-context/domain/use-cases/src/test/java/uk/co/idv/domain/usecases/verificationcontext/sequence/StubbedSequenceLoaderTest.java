package uk.co.idv.domain.usecases.verificationcontext.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.verificationcontext.StubVerificationSequencesEligible;
import uk.co.idv.domain.entities.verificationcontext.StubVerificationSequencesIneligible;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;

import static org.assertj.core.api.Assertions.assertThat;

class StubbedSequenceLoaderTest {

    private final SequenceLoader loader = new StubbedSequenceLoader();

    @Test
    void shouldReturnEligibleVerificationSequencesIfProvidedAliasValueDoesNotEndInNine() {
        final Alias providedAlias = AliasesMother.creditCardNumber("4929001111111111");
        final LoadSequencesRequest request = toRequest(providedAlias);

        final VerificationSequences sequences = loader.loadSequences(request);

        assertThat(sequences)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(new StubVerificationSequencesEligible());
    }

    @Test
    void shouldReturnIneligibleVerificationSequencesIfProvidedAliasValueEndsInNine() {
        final Alias providedAlias = AliasesMother.creditCardNumber("4929001111111119");
        final LoadSequencesRequest request = toRequest(providedAlias);

        final VerificationSequences sequences = loader.loadSequences(request);

        assertThat(sequences)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(new StubVerificationSequencesIneligible());
    }

    private static LoadSequencesRequest toRequest(final Alias providedAlias) {
        return LoadSequencesRequest.builder()
                .providedAlias(providedAlias)
                .build();
    }

}
