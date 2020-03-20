package uk.co.idv.onetimepasscode.sender.sns;

import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeDeliveryMother;
import uk.co.idv.onetimepasscode.sender.sns.attributes.MessageAttributeBuilder;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OneTimePasscodeDeliveryConverterTest {

    private final MessageAttributeBuilder attributeBuilder = mock(MessageAttributeBuilder.class);

    private final OneTimePasscodeDeliveryConverter converter = new OneTimePasscodeDeliveryConverter(attributeBuilder);

    @Test
    void shouldPopulateMessageOnPublishRequest() {
        final OneTimePasscodeDelivery delivery = OneTimePasscodeDeliveryMother.smsDelivery();

        final PublishRequest request = converter.toPublishRequest(delivery);

        assertThat(request.getMessage()).isEqualTo(delivery.getMessage());
    }

    @Test
    void shouldPopulatePhoneNumberOnPublishRequest() {
        final OneTimePasscodeDelivery delivery = OneTimePasscodeDeliveryMother.smsDelivery();

        final PublishRequest request = converter.toPublishRequest(delivery);

        assertThat(request.getPhoneNumber()).isEqualTo(delivery.getMethod().getValue());
    }

    @Test
    void shouldPopulateMessageAttributesOnPublishRequest() {
        final Map<String, MessageAttributeValue> attributes = Collections.emptyMap();
        given(attributeBuilder.build()).willReturn(attributes);
        final OneTimePasscodeDelivery delivery = OneTimePasscodeDeliveryMother.smsDelivery();

        final PublishRequest request = converter.toPublishRequest(delivery);

        assertThat(request.getMessageAttributes()).isEqualTo(attributes);
    }

}
