package uk.co.idv.domain.usecases.identity.data;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.phonenumber.MobilePhoneNumber;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequestMother;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneNumberLoaderTest {

    private final PhoneNumberLoader loader = new PhoneNumberLoader();

    @Test
    void shouldReturnTwoMobileNumbersIfAliasValueEndsInNine() {
        final Alias alias = AliasesMother.creditCardNumber("4929001111111119");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final PhoneNumbers numbers = loader.load(request);

        assertThat(numbers).hasSize(2);
    }

    @Test
    void shouldConvertCardNumberAliasToPhoneNumberByTrimmingProvidedAliasValueTo11Chars() {
        final Alias alias = AliasesMother.creditCardNumber("4929001111111119");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final PhoneNumbers numbers = loader.load(request);

        assertThat(numbers).contains(new MobilePhoneNumber("49290011111"));
    }

    @Test
    @ClearSystemProperty(key = "stubbed.phone.number")
    void shouldReturnDefaultPhoneNumberIfStubbedMobileNumberSystemPropertyIsNotSet() {
        final Alias alias = AliasesMother.creditCardNumber("4929001111111119");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final PhoneNumbers numbers = loader.load(request);

        assertThat(numbers).contains(new MobilePhoneNumber("07809386681"));
    }

    @Test
    @SetSystemProperty(key = "stubbed.phone.number", value = "07809123456")
    void shouldReturnDefaultStubbedMobileNumberFromSystemPropertyIfSet() {
        final Alias alias = AliasesMother.creditCardNumber("4929001111111119");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final PhoneNumbers numbers = loader.load(request);

        assertThat(numbers).contains(new MobilePhoneNumber("07809123456"));
    }

}
