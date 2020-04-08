package uk.co.idv.domain.usecases.onetimepasscode;

import lombok.Builder;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible;
import uk.co.idv.domain.usecases.onetimepasscode.generator.PasscodeGenerator;
import uk.co.idv.domain.usecases.onetimepasscode.message.OneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.util.TimeGenerator;

import java.util.UUID;

@Builder
public class OneTimePasscodeSender {

    private final OneTimePasscodeVerificationContextLoader contextLoader;
    private final OneTimePasscodeVerificationFactory verificationFactory;
    private final OneTimePasscodeVerificationLoader verificationLoader;
    private final PasscodeGenerator passcodeGenerator;
    private final OneTimePasscodeMessageBuilder messageBuilder;
    private final TimeGenerator timeGenerator;
    private final OneTimePasscodeDeliverySender sender;
    private final OneTimePasscodeVerificationDao dao;

    public OneTimePasscodeVerification send(final SendOneTimePasscodeRequest request) {
        final VerificationContext context = contextLoader.load(request.getContextId());
        final OneTimePasscodeVerification verification = verificationFactory.build(context);
        return send(context, verification, request.getDeliveryMethodId());
    }

    public OneTimePasscodeVerification send(final ResendOneTimePasscodeRequest request) {
        final VerificationContext context = contextLoader.load(request.getContextId());
        final OneTimePasscodeVerification verification = verificationLoader.load(request.getId());
        return send(context, verification, request.getDeliveryMethodId());
    }

    private OneTimePasscodeVerification send(final VerificationContext context,
                                             final OneTimePasscodeVerification verification,
                                             final UUID deliveryMethodId) {
        final OneTimePasscodeEligible method = context.getNextOneTimePasscodeEligibleMethod();
        final OneTimePasscodeDelivery delivery = toDelivery(deliveryMethodId, method, context.getActivity());
        sender.send(delivery);
        return update(verification, delivery);
    }

    private OneTimePasscodeDelivery toDelivery(final UUID deliveryMethodId,
                                               final OneTimePasscodeEligible method,
                                               final Activity activity) {
        final String passcode = passcodeGenerator.generate(method.getPasscodeLength());
        return OneTimePasscodeDelivery.builder()
                .passcode(passcode)
                .method(method.getDeliveryMethod(deliveryMethodId))
                .message(messageBuilder.build(activity, passcode))
                .sent(timeGenerator.now())
                .build();
    }

    private OneTimePasscodeVerification update(final OneTimePasscodeVerification verification,
                                               final OneTimePasscodeDelivery delivery) {
        verification.record(delivery);
        dao.save(verification);
        return verification;
    }

}
