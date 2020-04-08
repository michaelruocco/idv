package uk.co.idv.domain.usecases.onetimepasscode.message;

import uk.co.idv.domain.entities.activity.Activity;

public interface OneTimePasscodeMessageBuilder {

    boolean supports(final String activityName);

    String build(final Activity activity, final String passcode);

}
