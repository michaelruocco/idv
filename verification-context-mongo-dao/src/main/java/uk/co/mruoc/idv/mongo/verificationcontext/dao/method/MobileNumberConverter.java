package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobileNumber;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class MobileNumberConverter {

    public Collection<MobileNumber> toMobileNumbers(final Collection<MobileNumberDocument> documents) {
        return documents.stream()
                .map(this::toMobileNumber)
                .collect(Collectors.toList());
    }

    public MobileNumber toMobileNumber(final MobileNumberDocument document) {
        return new MobileNumber(UUID.fromString(document.getId()), document.getNumber());
    }

    public Collection<MobileNumberDocument> toDocuments(final Collection<MobileNumber> mobileNumbers) {
        return mobileNumbers.stream()
                .map(this::toDocument)
                .collect(Collectors.toList());
    }

    public MobileNumberDocument toDocument(final MobileNumber mobileNumber) {
        final MobileNumberDocument document = new MobileNumberDocument();
        document.setId(mobileNumber.getId().toString());
        document.setNumber(mobileNumber.getNumber());
        return document;
    }

}
