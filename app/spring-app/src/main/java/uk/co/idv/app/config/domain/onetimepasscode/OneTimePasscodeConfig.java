package uk.co.idv.app.config.domain.onetimepasscode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.config.uk.domain.onetimepasscode.UkOneTimePasscodeConfig;
import uk.co.idv.domain.usecases.onetimepasscode.send.OneTimePasscodeDeliverySender;
import uk.co.idv.domain.usecases.onetimepasscode.send.OneTimePasscodeSender;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeService;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationDao;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader;
import uk.co.idv.domain.usecases.verificationcontext.result.VerificationContextResultRecorder;

@Configuration
public class OneTimePasscodeConfig {

    private final UkOneTimePasscodeConfig config = new UkOneTimePasscodeConfig();

    @Bean
    public OneTimePasscodeService oneTimePasscodeService(final OneTimePasscodeVerificationDao dao,
                                                         final VerificationContextResultRecorder resultRecorder,
                                                         final OneTimePasscodeSender sender) {
        return config.oneTimePasscodeService(dao, resultRecorder, sender);
    }

    @Bean
    public OneTimePasscodeSender oneTimePasscodeSender(final VerificationContextLoader contextLoader,
                                                       final OneTimePasscodeDeliverySender sender,
                                                       final OneTimePasscodeVerificationDao dao) {
        return config.oneTimePasscodeSender(contextLoader, sender, dao);
    }

}
