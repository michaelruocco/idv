package uk.co.idv.repository.mongo.verificationcontext;

import lombok.RequiredArgsConstructor;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodsDocumentConverter;
import uk.co.idv.domain.entities.verificationcontext.MultipleMethodSequence;
import uk.co.idv.domain.entities.verificationcontext.SingleMethodSequence;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;

import java.util.Collection;

@RequiredArgsConstructor
public class VerificationSequenceDocumentConverter {

    private final VerificationMethodsDocumentConverter methodsConverter;

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
