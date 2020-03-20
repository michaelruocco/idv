package uk.co.idv.onetimepasscode.sender.sns;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeDeliveryMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SnsOneTimePasscodeDeliverySenderTest {

    private final OneTimePasscodeDelivery delivery = OneTimePasscodeDeliveryMother.smsDelivery();
    private final PublishRequest request = new PublishRequest();
    private final PublishResult publishResult = new PublishResult().withMessageId("expected-id");

    private final OneTimePasscodeDeliveryConverter converter = mock(OneTimePasscodeDeliveryConverter.class);
    private final AmazonSNS client = mock(AmazonSNS.class);

    private final SnsOneTimePasscodeDeliverySender sender = SnsOneTimePasscodeDeliverySender.builder()
            .converter(converter)
            .client(client)
            .build();

    @Test
    void shouldConvertDeliveryToPublishRequestAndPublish() {
        given(converter.toPublishRequest(delivery)).willReturn(request);
        given(client.publish(request)).willReturn(publishResult);

        sender.send(delivery);

        verify(client).publish(request);
    }

    @Test
    void shouldReturnMessageIdFromPublishResult() {
        given(converter.toPublishRequest(delivery)).willReturn(request);
        given(client.publish(request)).willReturn(publishResult);

        final String id = sender.send(delivery);

        assertThat(id).isEqualTo(publishResult.getMessageId());
    }
}
