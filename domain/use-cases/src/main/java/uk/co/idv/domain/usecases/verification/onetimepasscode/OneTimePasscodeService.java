package uk.co.idv.domain.usecases.verification.onetimepasscode;

import lombok.Builder;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader;

import java.util.UUID;

@Builder
public class OneTimePasscodeService {

    private final VerificationContextLoader contextLoader;
    private final OneTimePasscodeVerificationFactory verificationFactory;
    private final OneTimePasscodeVerificationLoader verificationLoader;
    private final PasscodeGenerator passcodeGenerator;
    private final OneTimePasscodeSender sender;
    private final OneTimePasscodeVerificationDao dao;

    public OneTimePasscodeVerification sendPasscode(final CreateOneTimePasscodeVerificationRequest request) {
        final VerificationContext context = contextLoader.load(request.getContextId());
        final OneTimePasscodeEligible method = context.getNextEligibleMethod(OneTimePasscode.NAME, OneTimePasscodeEligible.class);
        final OneTimePasscodeVerification verification = verificationFactory.build(method);
        return sendPasscode(method, verification, request.getDeliveryMethodId(), context.getActivity());
    }

    public OneTimePasscodeVerification sendPasscode(final UpdateOneTimePasscodeVerificationRequest request) {
        final VerificationContext context = contextLoader.load(request.getContextId());
        final OneTimePasscodeEligible method = context.getNextEligibleMethod(OneTimePasscode.NAME, OneTimePasscodeEligible.class);
        final OneTimePasscodeVerification verification = verificationLoader.load(request.getId());
        return sendPasscode(method, verification, request.getDeliveryMethodId(), context.getActivity());
    }

    private OneTimePasscodeVerification sendPasscode(final OneTimePasscodeEligible method,
                                                     final OneTimePasscodeVerification verification,
                                                     final UUID deliveryMethodId,
                                                     final Activity activity) {
        final OneTimePasscodeDelivery delivery = toDelivery(deliveryMethodId, method, activity);
        sender.send(delivery);
        return update(verification, delivery);
    }

    private OneTimePasscodeDelivery toDelivery(final UUID deliveryMethodId,
                                               final OneTimePasscodeEligible method,
                                               final Activity activity) {
        return OneTimePasscodeDelivery.builder()
                .passcode(passcodeGenerator.generate(method.getPasscodeLength()))
                .method(method.getDeliveryMethod(deliveryMethodId))
                .activity(activity)
                .build();
    }

    private OneTimePasscodeVerification update(final OneTimePasscodeVerification verification,
                                               final OneTimePasscodeDelivery delivery) {
        verification.record(delivery);
        dao.save(verification);
        return verification;
    }

}
