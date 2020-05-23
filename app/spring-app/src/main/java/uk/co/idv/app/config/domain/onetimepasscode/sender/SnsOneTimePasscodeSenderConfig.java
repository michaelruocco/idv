package uk.co.idv.app.config.domain.onetimepasscode.sender;

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

    @Bean
    public OneTimePasscodeDeliverySender deliverySender() {
        return SnsOneTimePasscodeDeliverySender.builder()
                .converter(new OneTimePasscodeDeliveryConverter(new MessageAttributeBuilder()))
                .client(new SnsClientFactory().build())
                .build();
    }

}
