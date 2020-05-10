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

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneNumberLoaderTest {

    private final PhoneNumberLoader loader = new PhoneNumberLoader();

    @Test
    void shouldReturnTwoMobileNumbersIfAliasValueDoesNotEndInNine() {
        final Alias alias = AliasesMother.creditCardNumber("4929001111111111");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final PhoneNumbers numbers = loader.load(request);

        assertThat(numbers).hasSize(2);
    }

    @Test
    void shouldReturnEmptyMobileNumbersIfAliasValueEndsInNine() {
        final Alias alias = AliasesMother.creditCardNumber("4929001111111119");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final PhoneNumbers numbers = loader.load(request);

        assertThat(numbers).isEmpty();
    }

    @Test
    void shouldConvertCardNumberAliasToPhoneNumberByTrimmingProvidedAliasValueTo11Chars() {
        final Alias alias = AliasesMother.creditCardNumber("4929001111111111");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final PhoneNumbers numbers = loader.load(request);

        assertThat(numbers).contains(MobilePhoneNumber.builder()
                .id(UUID.fromString("6837f49b-c19d-43dc-a2fd-0755bd09bcc5"))
                .value("49290011111")
                .build());
    }

    @Test
    @ClearSystemProperty(key = "stubbed.phone.number")
    void shouldReturnDefaultPhoneNumberIfStubbedMobileNumberSystemPropertyIsNotSet() {
        final Alias alias = AliasesMother.creditCardNumber("4929001111111111");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final PhoneNumbers numbers = loader.load(request);

        assertThat(numbers).contains(MobilePhoneNumber.builder()
                .id(UUID.fromString("8c8b65ef-ceb7-413a-98b8-e72b611cca64"))
                .value("07809386681")
                .build());
    }

    @Test
    @SetSystemProperty(key = "stubbed.phone.number", value = "07809123456")
    void shouldReturnDefaultStubbedMobileNumberFromSystemPropertyIfSet() {
        final Alias alias = AliasesMother.creditCardNumber("4929001111111111");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final PhoneNumbers numbers = loader.load(request);

        assertThat(numbers).contains(MobilePhoneNumber.builder()
                .id(UUID.fromString("8c8b65ef-ceb7-413a-98b8-e72b611cca64"))
                .value("07809123456")
                .build());
    }

}
