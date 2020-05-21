package uk.co.idv.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.domain.usecases.onetimepasscode.send.OneTimePasscodeDeliverySender;
import uk.co.idv.domain.usecases.util.id.IdGenerator;
import uk.co.idv.onetimepasscode.sender.InMemoryRecordingOneTimePasscodeDeliverySender;

@Configuration
@Profile("stubbed")
public class InMemoryOneTimePasscodeSenderConfig {

    @Bean
    public OneTimePasscodeDeliverySender oneTimePasscodeDeliverySender(final IdGenerator idGenerator) {
        return new InMemoryRecordingOneTimePasscodeDeliverySender(idGenerator);
    }

}
