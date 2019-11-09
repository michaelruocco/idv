package uk.co.idv.repository.mongo.verificationcontext.method;

import uk.co.idv.domain.usecases.exception.MethodNotSupportedException;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;

import java.util.Collection;
import java.util.Optional;

public class VerificationMethodDocumentConverterDelegator {

    private final Collection<VerificationMethodDocumentConverter> converters;

    public VerificationMethodDocumentConverterDelegator(final Collection<VerificationMethodDocumentConverter> converters) {
        this.converters = converters;
    }

    public VerificationMethod toMethod(final VerificationMethodDocument document) {
        final String name = document.getName();
        final Optional<VerificationMethodDocumentConverter> methodConverter = findConverter(name);
        return methodConverter
                .map(converter -> converter.toMethod(document))
                .orElseThrow(() -> new MethodNotSupportedException(name));
    }

    public VerificationMethodDocument toDocument(final VerificationMethod method) {
        final String name = method.getName();
        final Optional<VerificationMethodDocumentConverter> methodConverter = findConverter(name);
        return methodConverter
                .map(converter -> converter.toDocument(method))
                .orElseThrow(() -> new MethodNotSupportedException(name));
    }

    private Optional<VerificationMethodDocumentConverter> findConverter(final String methodName) {
        return converters.stream()
                .filter(converter -> converter.supportsMethod(methodName))
                .findFirst();
    }

}
