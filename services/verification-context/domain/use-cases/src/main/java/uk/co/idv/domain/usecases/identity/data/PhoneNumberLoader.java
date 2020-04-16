package uk.co.idv.domain.usecases.identity.data;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.phonenumber.MobilePhoneNumber;
import uk.co.idv.domain.entities.phonenumber.PhoneNumber;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;

@Slf4j
public class PhoneNumberLoader {

    private static final int LENGTH = 11;

    public PhoneNumbers load(final UpsertIdentityRequest request) {
        final Alias providedAlias = request.getProvidedAlias();
        if (valueEndsWithNine(providedAlias)) {
            final PhoneNumber number1 = new MobilePhoneNumber(toPhoneNumber(providedAlias));
            final PhoneNumber number2 = new MobilePhoneNumber(loadStubbedPhoneNumber());
            return new PhoneNumbers(number1, number2);
        }
        return new PhoneNumbers();
    }

    private boolean valueEndsWithNine(final Alias alias) {
        return alias.getValue().endsWith("9");
    }

    private String loadStubbedPhoneNumber() {
        final String phoneNumber = System.getProperty("stubbed.phone.number", "07809386681");
        log.info("loaded system property stubbed.phone.number {}", phoneNumber);
        return phoneNumber;
    }

    private String toPhoneNumber(final Alias providedAlias) {
        return providedAlias.getValue().substring(0, LENGTH);
    }

}
