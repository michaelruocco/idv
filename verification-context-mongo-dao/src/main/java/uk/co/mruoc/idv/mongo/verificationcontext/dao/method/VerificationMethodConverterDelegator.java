package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import uk.co.mruoc.idv.domain.exception.MethodNotSupportedException;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.util.Collection;
import java.util.Optional;

public class VerificationMethodConverterDelegator {

    private final Collection<VerificationMethodConverter> converters;

    public VerificationMethodConverterDelegator(final Collection<VerificationMethodConverter> converters) {
        this.converters = converters;
    }

    public VerificationMethod toMethod(final VerificationMethodDocument document) {
        final String name = document.getName();
        final Optional<VerificationMethodConverter> methodConverter = findConverter(name);
        return methodConverter
                .map(converter -> converter.toMethod(document))
                .orElseThrow(() -> new MethodNotSupportedException(name));
    }

    public VerificationMethodDocument toDocument(final VerificationMethod method) {
        final String name = method.getName();
        final Optional<VerificationMethodConverter> methodConverter = findConverter(name);
        return methodConverter
                .map(converter -> converter.toDocument(method))
                .orElseThrow(() -> new MethodNotSupportedException(name));
    }

    private Optional<VerificationMethodConverter> findConverter(final String methodName) {
        return converters.stream()
                .filter(converter -> converter.supportsMethod(methodName))
                .findFirst();
    }

}
