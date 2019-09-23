package uk.co.mruoc.idv.verificationcontext.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationSequences;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;

import static org.assertj.core.api.Assertions.assertThat;

class StubbedSequenceLoaderTest {

    private final SequenceLoader loader = new StubbedSequenceLoader();

    @Test
    void shouldReturnFakeVerificationSequences() {
        final VerificationSequences expectedSequences = new FakeVerificationSequences();

        final VerificationSequences sequences = loader.loadSequences(null);

        assertThat(sequences)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedSequences);
    }

}
