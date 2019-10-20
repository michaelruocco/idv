package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.util.Collection;
import java.util.stream.Collectors;

public class VerificationMethodsConverter {

    private final VerificationMethodConverterDelegator methodConverter;

    public VerificationMethodsConverter(VerificationMethodConverterDelegator methodConverter) {
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
