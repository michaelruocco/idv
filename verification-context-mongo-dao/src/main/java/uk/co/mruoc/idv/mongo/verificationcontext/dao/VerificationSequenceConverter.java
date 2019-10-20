package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import lombok.Builder;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.VerificationMethodsConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.MultipleMethodSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.SingleMethodSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.util.Collection;

@Builder
public class VerificationSequenceConverter {

    private final VerificationMethodsConverter methodsConverter;

    public VerificationSequence toSequence(final VerificationSequenceDocument document) {
        final Collection<VerificationMethod> methods = methodsConverter.toMethods(document.getMethods());
        if (methods.size() == 1) {
            return new SingleMethodSequence(methods.iterator().next());
        }
        return new MultipleMethodSequence(methods);
    }

    public VerificationSequenceDocument toDocument(final VerificationSequence sequence) {
        final VerificationSequenceDocument document = new VerificationSequenceDocument();
        document.setName(sequence.getName());
        document.setMethods(methodsConverter.toDocuments(sequence.getMethods()));
        return document;
    }

}
