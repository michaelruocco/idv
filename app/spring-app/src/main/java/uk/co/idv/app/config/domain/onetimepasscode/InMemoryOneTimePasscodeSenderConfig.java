package uk.co.idv.app.config.domain.onetimepasscode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.domain.usecases.onetimepasscode.send.OneTimePasscodeDeliverySender;
import uk.co.idv.domain.usecases.util.id.RandomIdGenerator;
import uk.co.idv.onetimepasscode.sender.InMemoryRecordingOneTimePasscodeDeliverySender;

@Configuration
@Profile("stubbed")
public class InMemoryOneTimePasscodeSenderConfig {

    @Bean
    public OneTimePasscodeDeliverySender oneTimePasscodeDeliverySender() {
        return new InMemoryRecordingOneTimePasscodeDeliverySender(new RandomIdGenerator());
    }

}
