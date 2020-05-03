package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;

public interface PushNotification extends VerificationMethod {

    String NAME = "push-notification";

    //TODO move this up to verification method interface once all methods are using parameters
    VerificationMethodParams getParameters();

}
