package uk.co.idv.repository.dynamo.verification.onetimepasscode;

import com.amazonaws.services.dynamodbv2.document.Item;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationMother;
import uk.co.idv.repository.dynamo.table.TimeToLiveCalculator;
import uk.co.idv.utils.json.converter.JsonConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OneTimePasscodeVerificationItemConverterTest {

    private final JsonConverter jsonConverter = mock(JsonConverter.class);
    private final TimeToLiveCalculator timeToLiveCalculator = mock(TimeToLiveCalculator.class);

    private final OneTimePasscodeVerificationItemConverter converter = OneTimePasscodeVerificationItemConverter.builder()
            .jsonConverter(jsonConverter)
            .timeToLiveCalculator(timeToLiveCalculator)
            .build();

    @Test
    void shouldSetIdOnItem() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();
        given(jsonConverter.toJson(verification)).willReturn("{}");

        final Item item = converter.toItem(verification);

        assertThat(item.getString("id")).isEqualTo(verification.getId().toString());
    }

    @Test
    void shouldSetTimeToLiveOnItem() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();
        final long expectedTimeToLive = 123;
        given(timeToLiveCalculator.calculate()).willReturn(expectedTimeToLive);
        given(jsonConverter.toJson(verification)).willReturn("{}");

        final Item item = converter.toItem(verification);

        assertThat(item.getNumber("ttl").longValue()).isEqualTo(expectedTimeToLive);
    }

    @Test
    void shouldSetJsonBodyOnItem() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();
        final String expectedJson = "{}";
        given(jsonConverter.toJson(verification)).willReturn(expectedJson);

        final Item item = converter.toItem(verification);

        assertThat(item.getJSON("body")).isEqualTo(expectedJson);
    }

    @Test
    void shouldConvertJsonBodyToContext() {
        final String json = "{}";
        final Item item = new Item().withJSON("body", json);
        final OneTimePasscodeVerification expectedVerification = OneTimePasscodeVerificationMother.pending();
        given(jsonConverter.toObject(json, OneTimePasscodeVerification.class)).willReturn(expectedVerification);

        final OneTimePasscodeVerification verification = converter.toVerification(item);

        assertThat(verification).isEqualTo(expectedVerification);
    }

}
