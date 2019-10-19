package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

public interface VerificationMethodConverter {

    String getSupportedMethodName();

    VerificationMethod toMethod(final VerificationMethodDocument document);

    VerificationMethodDocument toDocument(final VerificationMethod method);

}
