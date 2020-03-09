package uk.co.idv.domain.usecases.verification.onetimepasscode.message;

import uk.co.idv.domain.entities.activity.Activity;

public interface OneTimePasscodeMessageBuilder {

    String build(final Activity activity, final String passcode);

}
