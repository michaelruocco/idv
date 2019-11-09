package uk.co.idv.repository.mongo.verificationcontext;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VerificationSequencesConverter {

    private final VerificationSequenceDocumentConverter sequenceConverter;

    public VerificationSequences toSequences(final Collection<VerificationSequenceDocument> documents) {
        final Collection<VerificationSequence> sequences = documents.stream()
                .map(sequenceConverter::toSequence)
                .collect(Collectors.toList());
        return new VerificationSequences(sequences);
    }

    public Collection<VerificationSequenceDocument> toDocuments(final VerificationSequences sequences) {
        return sequences.stream()
                .map(sequenceConverter::toDocument)
                .collect(Collectors.toList());
    }

}
