package uk.co.idv.repository.mongo.verificationcontext;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationSequencesConverterTest {

    private final VerificationSequence sequence1 = mock(VerificationSequence.class);
    private final VerificationSequence sequence2 = mock(VerificationSequence.class);

    private final VerificationSequenceDocument document1 = mock(VerificationSequenceDocument.class);
    private final VerificationSequenceDocument document2 = mock(VerificationSequenceDocument.class);

    private final VerificationSequenceDocumentConverter sequenceConverter = mock(VerificationSequenceDocumentConverter.class);

    private final VerificationSequencesConverter sequencesConverter = new VerificationSequencesConverter(sequenceConverter);

    @Test
    void shouldConvertMultipleSequences() {
        final VerificationSequences sequences = new VerificationSequences(sequence1, sequence2);
        given(sequenceConverter.toDocument(sequence1)).willReturn(document1);
        given(sequenceConverter.toDocument(sequence2)).willReturn(document2);

        final Collection<VerificationSequenceDocument> documents = sequencesConverter.toDocuments(sequences);

        assertThat(documents).containsExactly(document1, document2);
    }

    @Test
    void shouldConvertMultipleDocuments() {
        given(sequenceConverter.toSequence(document1)).willReturn(sequence1);
        given(sequenceConverter.toSequence(document2)).willReturn(sequence2);

        final VerificationSequences sequences = sequencesConverter.toSequences(Arrays.asList(document1, document2));

        assertThat(sequences).containsExactly(sequence1, sequence2);
    }

}
