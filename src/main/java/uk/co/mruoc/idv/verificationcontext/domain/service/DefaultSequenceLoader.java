package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.mruoc.idv.verificationcontext.domain.model.SingleMethodSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.DefaultPasscodeSettings;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.Eligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobileNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSmsEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentryEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PinsentryFunction;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class DefaultSequenceLoader implements SequenceLoader {

    @Override
    public Collection<VerificationSequence> loadSequences(final LoadSequenceRequest request) {
        final VerificationMethod pushNotification = new PushNotification(new Eligible());
        final VerificationMethod physicalPinsentry = new PhysicalPinsentryEligible(PinsentryFunction.RESPOND, Collections.singleton(new CardNumber("4929991234567890")));
        final VerificationMethod mobilePinsentry = new MobilePinsentry(new Eligible(), PinsentryFunction.RESPOND);
        final VerificationMethod oneTimePasscodeSms = new OneTimePasscodeSmsEligible(new DefaultPasscodeSettings(), Collections.singleton(new MobileNumber("07809385580")));
        return Arrays.asList(
                new SingleMethodSequence(pushNotification),
                new SingleMethodSequence(physicalPinsentry),
                new SingleMethodSequence(mobilePinsentry),
                new SingleMethodSequence(oneTimePasscodeSms)
        );
    }

}
