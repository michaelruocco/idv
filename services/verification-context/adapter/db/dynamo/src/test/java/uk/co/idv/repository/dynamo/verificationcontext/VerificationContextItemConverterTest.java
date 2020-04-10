package uk.co.idv.repository.dynamo.verificationcontext;

import com.amazonaws.services.dynamodbv2.document.Item;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContextMother;
import uk.co.idv.dynamo.ttl.TimeToLiveCalculator;
import uk.co.idv.utils.json.converter.JsonConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationContextItemConverterTest {

    private final JsonConverter jsonConverter = mock(JsonConverter.class);
    private final TimeToLiveCalculator timeToLiveCalculator = mock(TimeToLiveCalculator.class);

    private final VerificationContextItemConverter converter = VerificationContextItemConverter.builder()
            .jsonConverter(jsonConverter)
            .timeToLiveCalculator(timeToLiveCalculator)
            .build();

    @Test
    void shouldSetIdOnItem() {
        final VerificationContext context = VerificationContextMother.fake();
        given(jsonConverter.toJson(context)).willReturn("{}");

        final Item item = converter.toItem(context);

        assertThat(item.getString("id")).isEqualTo(context.getId().toString());
    }

    @Test
    void shouldSetTimeToLiveOnItem() {
        final VerificationContext context = VerificationContextMother.fake();
        final long expectedTimeToLive = 123;
        given(timeToLiveCalculator.calculate()).willReturn(expectedTimeToLive);
        given(jsonConverter.toJson(context)).willReturn("{}");

        final Item item = converter.toItem(context);

        assertThat(item.getNumber("ttl").longValue()).isEqualTo(expectedTimeToLive);
    }

    @Test
    void shouldSetJsonBodyOnItem() {
        final VerificationContext context = VerificationContextMother.fake();
        final String expectedJson = "{}";
        given(jsonConverter.toJson(context)).willReturn(expectedJson);

        final Item item = converter.toItem(context);

        assertThat(item.getJSON("body")).isEqualTo(expectedJson);
    }

    @Test
    void shouldConvertJsonBodyToContext() {
        final String json = "{}";
        final Item item = new Item().withJSON("body", json);
        final VerificationContext expectedContext = VerificationContextMother.fake();
        given(jsonConverter.toObject(json, VerificationContext.class)).willReturn(expectedContext);

        final VerificationContext context = converter.toContext(item);

        assertThat(context).isEqualTo(expectedContext);
    }

}
