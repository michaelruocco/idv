package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobileNumber;

import java.util.UUID;

public class MobileNumberDocumentConverter {

    public MobileNumber toMobileNumber(final MobileNumberDocument document) {
        return new MobileNumber(UUID.fromString(document.getId()), document.getNumber());
    }

    public MobileNumberDocument toDocument(final MobileNumber mobileNumber) {
        final MobileNumberDocument document = new MobileNumberDocument();
        document.setId(mobileNumber.getId().toString());
        document.setNumber(mobileNumber.getNumber());
        return document;
    }

}
