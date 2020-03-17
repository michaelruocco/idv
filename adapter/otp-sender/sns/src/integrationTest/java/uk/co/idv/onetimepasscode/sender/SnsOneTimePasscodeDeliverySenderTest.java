package uk.co.idv.onetimepasscode.sender;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.SmsDeliveryMethod;
import uk.co.idv.domain.usecases.verification.onetimepasscode.sender.OneTimePasscodeDeliverySender;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class SnsOneTimePasscodeDeliverySenderTest {

    private final MessageAttributeBuilder attributeBuilder = new MessageAttributeBuilder();
    private final OneTimePasscodeDeliveryConverter converter = new OneTimePasscodeDeliveryConverter(attributeBuilder);
    private final AmazonSNS client = AmazonSNSClientBuilder.defaultClient();

    private final OneTimePasscodeDeliverySender sender = SnsOneTimePasscodeDeliverySender.builder()
            .converter(converter)
            .client(client)
            .build();

    @Test
    public void shouldSendSms() {
        final OneTimePasscodeDelivery delivery = OneTimePasscodeDelivery.builder()
                .method(new SmsDeliveryMethod("+447809385580"))
                .message("Your IDV passcode is 12345678")
                .passcode("12345678")
                .sent(Instant.now())
                .build();

        final String id = sender.send(delivery);

        assertThat(id).isNotNull();
    }

}
