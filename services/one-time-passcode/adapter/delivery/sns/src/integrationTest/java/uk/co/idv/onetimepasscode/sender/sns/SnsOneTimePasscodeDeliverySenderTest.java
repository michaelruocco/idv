package uk.co.idv.onetimepasscode.sender.sns;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethodMother;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeDeliverySender;
import uk.co.idv.onetimepasscode.sender.sns.attributes.MessageAttributeBuilder;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class SnsOneTimePasscodeDeliverySenderTest {

    @Container
    public static final SnsLocalContainer SNS = new SnsLocalContainer();

    private final MessageAttributeBuilder attributeBuilder = new MessageAttributeBuilder();
    private final OneTimePasscodeDeliveryConverter converter = new OneTimePasscodeDeliveryConverter(attributeBuilder);
    private final OneTimePasscodeDeliverySender sender = SnsOneTimePasscodeDeliverySender.builder()
            .converter(converter)
            .client(SNS.buildSnsClient())
            .build();

    @Test
    public void shouldSendSms() {
        final OneTimePasscodeDelivery delivery = OneTimePasscodeDelivery.builder()
                .method(DeliveryMethodMother.sms())
                .message("Your IDV passcode is 12345678")
                .passcode("12345678")
                .sent(Instant.now())
                .build();

        final String id = sender.send(delivery);

        assertThat(id).isNotNull();
    }

}
