package uk.co.idv.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.domain.usecases.onetimepasscode.DefaultOneTimePasscodeService;
import uk.co.idv.domain.usecases.onetimepasscode.send.OneTimePasscodeDeliverySender;
import uk.co.idv.domain.usecases.onetimepasscode.send.OneTimePasscodeSender;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeService;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationContextLoader;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationConverter;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationDao;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationFactory;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationLoader;
import uk.co.idv.domain.usecases.onetimepasscode.verify.OneTimePasscodeVerifier;
import uk.co.idv.domain.usecases.onetimepasscode.verify.VerifyOneTimePasscodeRequestConverter;
import uk.co.idv.domain.usecases.onetimepasscode.generator.RandomPasscodeGenerator;
import uk.co.idv.domain.usecases.onetimepasscode.message.DefaultOneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.onetimepasscode.message.DelegatingOneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.onetimepasscode.message.OneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.onetimepasscode.message.OnlinePurchaseOneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.util.id.RandomIdGenerator;
import uk.co.idv.domain.usecases.util.time.CurrentTimeProvider;
import uk.co.idv.domain.usecases.util.time.TimeProvider;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader;
import uk.co.idv.domain.usecases.verificationcontext.result.VerificationContextResultRecorder;

@Configuration
public class OneTimePasscodeDomainConfig {

    private final TimeProvider timeProvider = new CurrentTimeProvider();

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
                                                       final OneTimePasscodeMessageBuilder messageBuilder,
                                                       final OneTimePasscodeDeliverySender sender,
                                                       final OneTimePasscodeVerificationDao dao) {
        return OneTimePasscodeSender.builder()
                .contextLoader(contextLoader)
                .verificationFactory(verificationFactory)
                .verificationLoader(verificationLoader)
                .passcodeGenerator(new RandomPasscodeGenerator())
                .messageBuilder(messageBuilder)
                .timeProvider(timeProvider)
                .sender(sender)
                .dao(dao)
                .build();
    }

    @Bean
    public OneTimePasscodeVerificationLoader oneTimePasscodeVerificationLoader(final OneTimePasscodeVerificationDao dao) {
        return OneTimePasscodeVerificationLoader.builder()
                .timeProvider(timeProvider)
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
    public OneTimePasscodeVerificationFactory oneTimePasscodeVerificationFactory() {
        return OneTimePasscodeVerificationFactory.builder()
                .timeProvider(timeProvider)
                .idGenerator(new RandomIdGenerator())
                .build();
    }

    @Bean
    public OneTimePasscodeVerificationContextLoader oneTimePasscodeVerificationContextLoader(final VerificationContextLoader contextLoader) {
        return new OneTimePasscodeVerificationContextLoader(contextLoader);
    }

    @Bean
    public OneTimePasscodeMessageBuilder messageBuilder() {
        return new DelegatingOneTimePasscodeMessageBuilder(
                new OnlinePurchaseOneTimePasscodeMessageBuilder(),
                new DefaultOneTimePasscodeMessageBuilder()
        );
    }

    @Bean
    public VerifyOneTimePasscodeRequestConverter requestConverter() {
        return new VerifyOneTimePasscodeRequestConverter(timeProvider);
    }

    @Bean
    public OneTimePasscodeVerificationConverter verificationConverter() {
        return new OneTimePasscodeVerificationConverter(timeProvider);
    }

}
