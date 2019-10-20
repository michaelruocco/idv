package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import uk.co.mruoc.idv.domain.exception.MethodNotSupportedException;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VerificationMethodConverterDelegator {

    private final Map<String, VerificationMethodConverter> converters = new HashMap<>();

    public VerificationMethodConverterDelegator(final Collection<VerificationMethodConverter> converters) {
        converters.forEach(converter -> this.converters.put(converter.getSupportedMethodName(), converter));
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

    private Optional<VerificationMethodConverter> findConverter(final String activityName) {
        return Optional.ofNullable(converters.get(activityName));
    }

}
