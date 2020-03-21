package uk.co.idv.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.domain.usecases.util.IdGenerator;
import uk.co.idv.domain.usecases.verification.onetimepasscode.sender.OneTimePasscodeDeliverySender;
import uk.co.idv.onetimepasscode.sender.InMemoryRecordingOneTimePasscodeDeliverySender;

@Configuration
@Profile("stub")
public class InMemoryOneTimePasscodeSenderConfig {

    @Bean
    public OneTimePasscodeDeliverySender oneTimePasscodeDeliverySender(final IdGenerator idGenerator) {
        return new InMemoryRecordingOneTimePasscodeDeliverySender(idGenerator);
    }

}
