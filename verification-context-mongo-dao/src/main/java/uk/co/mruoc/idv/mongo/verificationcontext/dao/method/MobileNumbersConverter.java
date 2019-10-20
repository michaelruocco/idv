package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobileNumber;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MobileNumbersConverter {

    private final MobileNumberConverter mobileNumberConverter;

    public Collection<MobileNumber> toMobileNumbers(final Collection<MobileNumberDocument> documents) {
        return documents.stream()
                .map(mobileNumberConverter::toMobileNumber)
                .collect(Collectors.toList());
    }

    public Collection<MobileNumberDocument> toDocuments(final Collection<MobileNumber> mobileNumbers) {
        return mobileNumbers.stream()
                .map(mobileNumberConverter::toDocument)
                .collect(Collectors.toList());
    }

}
