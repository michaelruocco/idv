package uk.co.idv.domain.usecases.verification.onetimepasscode;

import lombok.Builder;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible;
import uk.co.idv.domain.usecases.verification.onetimepasscode.generator.PasscodeGenerator;
import uk.co.idv.domain.usecases.verification.onetimepasscode.message.OneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.verification.onetimepasscode.sender.OneTimePasscodeSender;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader;

import java.util.UUID;

@Builder
public class DefaultOneTimePasscodeService implements OneTimePasscodeService {

    private final VerificationContextLoader contextLoader;
    private final OneTimePasscodeVerificationFactory verificationFactory;
    private final OneTimePasscodeVerificationLoader verificationLoader;
    private final PasscodeGenerator passcodeGenerator;
    private final OneTimePasscodeMessageBuilder messageBuilder;
    private final OneTimePasscodeSender sender;
    private final OneTimePasscodeVerificationDao dao;

    @Override
    public OneTimePasscodeVerification sendPasscode(final CreateOneTimePasscodeVerificationRequest request) {
        final VerificationContext context = contextLoader.load(request.getContextId());
        final OneTimePasscodeVerification verification = verificationFactory.build(context);
        return sendPasscode(context, verification, request.getDeliveryMethodId());
    }

    @Override
    public OneTimePasscodeVerification sendPasscode(final UpdateOneTimePasscodeVerificationRequest request) {
        final VerificationContext context = contextLoader.load(request.getContextId());
        final OneTimePasscodeVerification verification = verificationLoader.load(request.getId());
        return sendPasscode(context, verification, request.getDeliveryMethodId());
    }

    @Override
    public OneTimePasscodeVerification load(final UUID id) {
        return verificationLoader.load(id);
    }

    private OneTimePasscodeVerification sendPasscode(final VerificationContext context,
                                                     final OneTimePasscodeVerification verification,
                                                     final UUID deliveryMethodId) {
        final OneTimePasscodeEligible method = context.getNextEligibleMethod(OneTimePasscode.NAME, OneTimePasscodeEligible.class);
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
                .build();
    }

    private OneTimePasscodeVerification update(final OneTimePasscodeVerification verification,
                                               final OneTimePasscodeDelivery delivery) {
        verification.record(delivery);
        dao.save(verification);
        return verification;
    }

}
