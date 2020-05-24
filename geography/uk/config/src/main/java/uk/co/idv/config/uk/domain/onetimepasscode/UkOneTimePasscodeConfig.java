package uk.co.idv.config.uk.domain.onetimepasscode;

import uk.co.idv.domain.usecases.onetimepasscode.DefaultOneTimePasscodeService;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeService;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationContextLoader;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationDao;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationFactory;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationLoader;
import uk.co.idv.domain.usecases.onetimepasscode.message.DefaultOneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.onetimepasscode.message.DelegatingOneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.onetimepasscode.message.OneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.onetimepasscode.message.OnlinePurchaseOneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.onetimepasscode.send.OneTimePasscodeDeliverySender;
import uk.co.idv.domain.usecases.onetimepasscode.send.OneTimePasscodeSender;
import uk.co.idv.domain.usecases.onetimepasscode.verify.OneTimePasscodeVerifier;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader;
import uk.co.idv.domain.usecases.verificationcontext.result.VerificationContextResultRecorder;

public class UkOneTimePasscodeConfig {

    public OneTimePasscodeService oneTimePasscodeService(final OneTimePasscodeVerificationDao dao,
                                                         final VerificationContextResultRecorder resultRecorder,
                                                         final OneTimePasscodeSender sender) {
        return DefaultOneTimePasscodeService.builder()
                .verificationLoader(verificationLoader(dao))
                .verifier(verifier(dao, resultRecorder))
                .sender(sender)
                .build();
    }

    public OneTimePasscodeSender oneTimePasscodeSender(final VerificationContextLoader contextLoader,
                                                       final OneTimePasscodeDeliverySender sender,
                                                       final OneTimePasscodeVerificationDao dao) {
        return OneTimePasscodeSender.builder()
                .contextLoader(oneTimePasscodeContextLoader(contextLoader))
                .verificationFactory(verificationFactory())
                .verificationLoader(verificationLoader(dao))
                .messageBuilder(messageBuilder())
                .sender(sender)
                .dao(dao)
                .build();
    }

    private OneTimePasscodeVerifier verifier(final OneTimePasscodeVerificationDao dao,
                                             final VerificationContextResultRecorder resultRecorder) {
        return OneTimePasscodeVerifier.builder()
                .verificationLoader(verificationLoader(dao))
                .dao(dao)
                .resultRecorder(resultRecorder)
                .build();
    }

    private OneTimePasscodeVerificationLoader verificationLoader(final OneTimePasscodeVerificationDao dao) {
        return OneTimePasscodeVerificationLoader.builder()
                .dao(dao)
                .build();
    }

    private OneTimePasscodeVerificationFactory verificationFactory() {
        return OneTimePasscodeVerificationFactory.builder().build();
    }

    private OneTimePasscodeVerificationContextLoader oneTimePasscodeContextLoader(final VerificationContextLoader contextLoader) {
        return new OneTimePasscodeVerificationContextLoader(contextLoader);
    }

    private OneTimePasscodeMessageBuilder messageBuilder() {
        return new DelegatingOneTimePasscodeMessageBuilder(
                new OnlinePurchaseOneTimePasscodeMessageBuilder(),
                new DefaultOneTimePasscodeMessageBuilder()
        );
    }

}
