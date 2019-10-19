package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import lombok.Builder;
import uk.co.mruoc.idv.verificationcontext.domain.model.MultipleMethodSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.SingleMethodSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.util.Collection;
import java.util.stream.Collectors;

@Builder
public class VerificationSequenceConverter {

    private final VerificationMethodConverterDelegator methodConverter;

    public VerificationSequences toSequences(final Collection<VerificationSequenceDocument> documents) {
        final Collection<VerificationSequence> sequences = documents.stream()
                .map(this::toSequence)
                .collect(Collectors.toList());
        return new VerificationSequences(sequences);
    }

    public VerificationSequence toSequence(final VerificationSequenceDocument document) {
        final Collection<VerificationMethod> methods = methodConverter.toMethods(document.getMethods());
        if (methods.size() == 1) {
            return new SingleMethodSequence(methods.iterator().next());
        }
        return new MultipleMethodSequence(methods);
    }

    public Collection<VerificationSequenceDocument> toDocuments(final VerificationSequences sequences) {
        return sequences.stream()
                .map(this::toDocument)
                .collect(Collectors.toList());
    }
    public VerificationSequenceDocument toDocument(final VerificationSequence sequence) {
        final Collection<VerificationMethodDocument> methods = methodConverter.toDocuments(sequence.getMethods());
        return VerificationSequenceDocument.builder()
                .name(sequence.getName())
                .methods(methods)
                .build();
    }

}
