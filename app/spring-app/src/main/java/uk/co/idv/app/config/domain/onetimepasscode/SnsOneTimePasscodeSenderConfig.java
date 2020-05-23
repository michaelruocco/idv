package uk.co.idv.app.config.domain.onetimepasscode;

import com.amazonaws.services.sns.AmazonSNS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.domain.usecases.onetimepasscode.send.OneTimePasscodeDeliverySender;
import uk.co.idv.onetimepasscode.sender.sns.OneTimePasscodeDeliveryConverter;
import uk.co.idv.onetimepasscode.sender.sns.SnsClientFactory;
import uk.co.idv.onetimepasscode.sender.sns.SnsOneTimePasscodeDeliverySender;
import uk.co.idv.onetimepasscode.sender.sns.attributes.MessageAttributeBuilder;

@Configuration
@Profile("!stubbed")
public class SnsOneTimePasscodeSenderConfig {

    private static final SnsClientFactory CLIENT_FACTORY = new SnsClientFactory();

    @Bean
    public OneTimePasscodeDeliverySender oneTimePasscodeDeliverySender(final OneTimePasscodeDeliveryConverter converter,
                                                                       final AmazonSNS client) {
        return SnsOneTimePasscodeDeliverySender.builder()
                .converter(converter)
                .client(client)
                .build();
    }

    @Bean
    public OneTimePasscodeDeliveryConverter oneTimePasscodeDeliveryConverter(final MessageAttributeBuilder attributeBuilder) {
        return new OneTimePasscodeDeliveryConverter(attributeBuilder);
    }

    @Bean
    public MessageAttributeBuilder messageAttributeBuilder() {
        return new MessageAttributeBuilder();
    }

    @Bean
    public AmazonSNS snsClient() {
        return CLIENT_FACTORY.build();
    }

}
