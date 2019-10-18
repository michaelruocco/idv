package uk.co.mruoc.idv.verificationcontext.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationSequencesEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationSequencesIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;

import static org.assertj.core.api.Assertions.assertThat;

class StubbedSequenceLoaderTest {

    private final SequenceLoader loader = new StubbedSequenceLoader();

    @Test
    void shouldReturnEligibleVerificationSequencesIfProvidedAliasValueDoesNotEndInNine() {
        final Alias providedAlias = new FakeCreditCardNumber("4929001111111111");
        final LoadSequencesRequest request = toRequest(providedAlias);

        final VerificationSequences sequences = loader.loadSequences(request);

        assertThat(sequences)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(new FakeVerificationSequencesEligible());
    }

    @Test
    void shouldReturnIneligibleVerificationSequencesIfProvidedAliasValueDoesNotEndInNine() {
        final Alias providedAlias = new FakeCreditCardNumber("4929001111111119");
        final LoadSequencesRequest request = toRequest(providedAlias);

        final VerificationSequences sequences = loader.loadSequences(request);

        assertThat(sequences)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(new FakeVerificationSequencesIneligible());
    }

    private static LoadSequencesRequest toRequest(final Alias providedAlias) {
        return LoadSequencesRequest.builder()
                .providedAlias(providedAlias)
                .build();
    }

}
