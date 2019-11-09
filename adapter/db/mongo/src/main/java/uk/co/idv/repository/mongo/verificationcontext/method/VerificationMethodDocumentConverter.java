package uk.co.idv.repository.mongo.verificationcontext.method;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;

public interface VerificationMethodDocumentConverter {

    boolean supportsMethod(final String methodName);

    VerificationMethod toMethod(final VerificationMethodDocument document);

    VerificationMethodDocument toDocument(final VerificationMethod method);

    static void populateCommonFields(final VerificationMethod method, final VerificationMethodDocument document) {
        document.setName(method.getName());
        document.setMaxAttempts(method.getMaxAttempts());
        document.setDuration(method.getDuration().toMillis());
    }

}
