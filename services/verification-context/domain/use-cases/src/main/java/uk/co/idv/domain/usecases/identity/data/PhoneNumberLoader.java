package uk.co.idv.domain.usecases.identity.data;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.phonenumber.MobilePhoneNumber;
import uk.co.idv.domain.entities.phonenumber.PhoneNumber;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;

import java.util.UUID;

@Slf4j
public class PhoneNumberLoader {

    private static final int LENGTH = 11;

    public PhoneNumbers load(final UpsertIdentityRequest request) {
        final Alias providedAlias = request.getProvidedAlias();
        if (valueEndsWithNine(providedAlias)) {
            final PhoneNumber number1 = toPhoneNumber(providedAlias);
            final PhoneNumber number2 = loadStubbedPhoneNumber();
            return new PhoneNumbers(number1, number2);
        }
        return new PhoneNumbers();
    }

    private boolean valueEndsWithNine(final Alias alias) {
        return alias.getValue().endsWith("9");
    }

    private PhoneNumber loadStubbedPhoneNumber() {
        return MobilePhoneNumber.builder()
                .id(UUID.fromString("8c8b65ef-ceb7-413a-98b8-e72b611cca64"))
                .value(loadStubbedPhoneNumberValue())
                .build();
    }

    private String loadStubbedPhoneNumberValue() {
        final String phoneNumber = System.getProperty("stubbed.phone.number", "07809386681");
        log.info("loaded system property stubbed.phone.number {}", phoneNumber);
        return phoneNumber;
    }

    private PhoneNumber toPhoneNumber(final Alias providedAlias) {
        return MobilePhoneNumber.builder()
                .id(UUID.fromString("6837f49b-c19d-43dc-a2fd-0755bd09bcc5"))
                .value(providedAlias.getValue().substring(0, LENGTH))
                .build();
    }

}
