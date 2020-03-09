package uk.co.idv.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.domain.usecases.util.IdGenerator;
import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.idv.domain.usecases.verification.onetimepasscode.DefaultOneTimePasscodeService;
import uk.co.idv.domain.usecases.verification.onetimepasscode.message.OneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.verification.onetimepasscode.message.OneTimePasscodeOnlinePurchaseMessageBuilder;
import uk.co.idv.domain.usecases.verification.onetimepasscode.sender.InMemoryRecordingOneTimePasscodeSender;
import uk.co.idv.domain.usecases.verification.onetimepasscode.sender.OneTimePasscodeSender;
import uk.co.idv.domain.usecases.verification.onetimepasscode.OneTimePasscodeService;
import uk.co.idv.domain.usecases.verification.onetimepasscode.OneTimePasscodeVerificationDao;
import uk.co.idv.domain.usecases.verification.onetimepasscode.OneTimePasscodeVerificationFactory;
import uk.co.idv.domain.usecases.verification.onetimepasscode.OneTimePasscodeVerificationLoader;
import uk.co.idv.domain.usecases.verification.onetimepasscode.generator.PasscodeGenerator;
import uk.co.idv.domain.usecases.verification.onetimepasscode.generator.RandomPasscodeGenerator;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader;

@Configuration
public class OneTimePasscodeDomainConfig {

    @Bean
    public OneTimePasscodeService oneTimePasscodeService(final VerificationContextLoader contextLoader,
                                                         final OneTimePasscodeVerificationFactory verificationFactory,
                                                         final OneTimePasscodeVerificationLoader verificationLoader,
                                                         final PasscodeGenerator passcodeGenerator,
                                                         final OneTimePasscodeMessageBuilder messageBuilder,
                                                         final OneTimePasscodeSender sender,
                                                         final OneTimePasscodeVerificationDao dao) {
        return DefaultOneTimePasscodeService.builder()
                .contextLoader(contextLoader)
                .verificationFactory(verificationFactory)
                .verificationLoader(verificationLoader)
                .passcodeGenerator(passcodeGenerator)
                .messageBuilder(messageBuilder)
                .sender(sender)
                .dao(dao)
                .build();
    }

    @Bean
    public OneTimePasscodeVerificationFactory oneTimePasscodeVerificationFactory(final TimeGenerator timeGenerator,
                                                                                 final IdGenerator idGenerator) {
        return OneTimePasscodeVerificationFactory.builder()
                .timeGenerator(timeGenerator)
                .idGenerator(idGenerator)
                .build();
    }

    @Bean
    public OneTimePasscodeVerificationLoader oneTimePasscodeVerificationLoader(final TimeGenerator timeGenerator,
                                                                               final OneTimePasscodeVerificationDao dao) {
        return OneTimePasscodeVerificationLoader.builder()
                .timeGenerator(timeGenerator)
                .dao(dao)
                .build();
    }

    @Bean
    public PasscodeGenerator passcodeGenerator() {
        return new RandomPasscodeGenerator();
    }

    @Bean
    public OneTimePasscodeSender oneTimePasscodeSender() {
        return new InMemoryRecordingOneTimePasscodeSender();
    }

    @Bean
    public OneTimePasscodeMessageBuilder messageBuilder() {
        return new OneTimePasscodeOnlinePurchaseMessageBuilder();
    }

}
