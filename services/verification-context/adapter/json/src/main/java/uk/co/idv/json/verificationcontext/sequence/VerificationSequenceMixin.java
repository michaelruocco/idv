package uk.co.idv.json.verificationcontext.sequence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;

import java.util.Optional;

public interface VerificationSequenceMixin {

    @JsonIgnore
    Optional<PhysicalPinsentry> getPhysicalPinsentry();

    @JsonIgnore
    Optional<MobilePinsentryEligible> getMobilePinsentry();

    @JsonIgnore
    Optional<PushNotification> getPushNotification();

    @JsonIgnore
    Optional<OneTimePasscode> getOneTimePasscode();

}
