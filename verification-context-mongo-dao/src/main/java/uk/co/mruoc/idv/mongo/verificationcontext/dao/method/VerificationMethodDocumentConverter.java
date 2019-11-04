package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

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
