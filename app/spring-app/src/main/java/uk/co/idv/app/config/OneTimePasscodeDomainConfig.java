package uk.co.idv.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.domain.usecases.util.IdGenerator;
import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.idv.domain.usecases.verification.onetimepasscode.DefaultOneTimePasscodeService;
import uk.co.idv.domain.usecases.verification.onetimepasscode.OneTimePasscodeSender;
import uk.co.idv.domain.usecases.verification.onetimepasscode.OneTimePasscodeVerificationContextLoader;
import uk.co.idv.domain.usecases.verification.onetimepasscode.OneTimePasscodeVerificationConverter;
import uk.co.idv.domain.usecases.verification.onetimepasscode.OneTimePasscodeVerifier;
import uk.co.idv.domain.usecases.verification.onetimepasscode.VerifyOneTimePasscodeRequestConverter;
import uk.co.idv.domain.usecases.verification.onetimepasscode.message.DefaultOneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.verification.onetimepasscode.message.DelegatingOneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.verification.onetimepasscode.message.OneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.verification.onetimepasscode.message.OnlinePurchaseOneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.verification.onetimepasscode.sender.OneTimePasscodeDeliverySender;
import uk.co.idv.domain.usecases.verification.onetimepasscode.OneTimePasscodeService;
import uk.co.idv.domain.usecases.verification.onetimepasscode.OneTimePasscodeVerificationDao;
import uk.co.idv.domain.usecases.verification.onetimepasscode.OneTimePasscodeVerificationFactory;
import uk.co.idv.domain.usecases.verification.onetimepasscode.OneTimePasscodeVerificationLoader;
import uk.co.idv.domain.usecases.verification.onetimepasscode.generator.PasscodeGenerator;
import uk.co.idv.domain.usecases.verification.onetimepasscode.generator.RandomPasscodeGenerator;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextResultRecorder;
import uk.co.idv.onetimepasscode.sender.InMemoryRecordingOneTimePasscodeDeliverySender;

@Configuration
public class OneTimePasscodeDomainConfig {

    @Bean
    public OneTimePasscodeService oneTimePasscodeService(final OneTimePasscodeSender sender,
                                                         final OneTimePasscodeVerificationLoader verificationLoader,
                                                         final OneTimePasscodeVerifier verifier) {
        return DefaultOneTimePasscodeService.builder()
                .sender(sender)
                .verificationLoader(verificationLoader)
                .verifier(verifier)
                .build();
    }

    @Bean
    public OneTimePasscodeSender oneTimePasscodeSender(final OneTimePasscodeVerificationContextLoader contextLoader,
                                                       final OneTimePasscodeVerificationFactory verificationFactory,
                                                       final OneTimePasscodeVerificationLoader verificationLoader,
                                                       final PasscodeGenerator passcodeGenerator,
                                                       final OneTimePasscodeMessageBuilder messageBuilder,
                                                       final OneTimePasscodeDeliverySender sender,
                                                       final OneTimePasscodeVerificationDao dao) {
        return OneTimePasscodeSender.builder()
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
    public OneTimePasscodeVerificationLoader oneTimePasscodeVerificationLoader(final TimeGenerator timeGenerator,
                                                                               final OneTimePasscodeVerificationDao dao) {
        return OneTimePasscodeVerificationLoader.builder()
                .timeGenerator(timeGenerator)
                .dao(dao)
                .build();
    }

    @Bean
    public OneTimePasscodeVerifier oneTimePasscodeVerifier(final OneTimePasscodeVerificationLoader verificationLoader,
                                                           final VerifyOneTimePasscodeRequestConverter requestConverter,
                                                           final OneTimePasscodeVerificationConverter verificationConverter,
                                                           final OneTimePasscodeVerificationDao dao,
                                                           final VerificationContextResultRecorder resultRecorder) {
        return OneTimePasscodeVerifier.builder()
                .verificationLoader(verificationLoader)
                .requestConverter(requestConverter)
                .verificationConverter(verificationConverter)
                .dao(dao)
                .resultRecorder(resultRecorder)
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
    public OneTimePasscodeVerificationContextLoader oneTimePasscodeVerificationContextLoader(final VerificationContextLoader contextLoader) {
        return new OneTimePasscodeVerificationContextLoader(contextLoader);
    }

    @Bean
    public PasscodeGenerator passcodeGenerator() {
        return new RandomPasscodeGenerator();
    }

    @Bean
    public OneTimePasscodeDeliverySender oneTimePasscodeDeliverySender(final IdGenerator idGenerator) {
        return new InMemoryRecordingOneTimePasscodeDeliverySender(idGenerator);
    }

    @Bean
    public OneTimePasscodeMessageBuilder messageBuilder() {
        return new DelegatingOneTimePasscodeMessageBuilder(
                new OnlinePurchaseOneTimePasscodeMessageBuilder(),
                new DefaultOneTimePasscodeMessageBuilder()
        );
    }

    @Bean
    public VerifyOneTimePasscodeRequestConverter requestConverter(final TimeGenerator timeGenerator) {
        return new VerifyOneTimePasscodeRequestConverter(timeGenerator);
    }

    @Bean
    public OneTimePasscodeVerificationConverter verificationConverter(final TimeGenerator timeGenerator) {
        return new OneTimePasscodeVerificationConverter(timeGenerator);
    }

}
