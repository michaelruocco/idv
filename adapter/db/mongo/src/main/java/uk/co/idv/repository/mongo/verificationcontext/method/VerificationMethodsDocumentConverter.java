package uk.co.idv.repository.mongo.verificationcontext.method;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;

import java.util.Collection;
import java.util.stream.Collectors;

public class VerificationMethodsDocumentConverter {

    private final VerificationMethodDocumentConverterDelegator methodConverter;

    public VerificationMethodsDocumentConverter(VerificationMethodDocumentConverterDelegator methodConverter) {
        this.methodConverter = methodConverter;
    }

    public Collection<VerificationMethod> toMethods(final Collection<VerificationMethodDocument> documents) {
        return documents.stream()
                .map(methodConverter::toMethod)
                .collect(Collectors.toList());
    }

    public Collection<VerificationMethodDocument> toDocuments(final Collection<VerificationMethod> methods) {
        return methods.stream()
                .map(methodConverter::toDocument)
                .collect(Collectors.toList());
    }

}
