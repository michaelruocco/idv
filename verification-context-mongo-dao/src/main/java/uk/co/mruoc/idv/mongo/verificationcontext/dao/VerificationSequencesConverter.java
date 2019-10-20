package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VerificationSequencesConverter {

    private final VerificationSequenceConverter sequenceConverter;

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
