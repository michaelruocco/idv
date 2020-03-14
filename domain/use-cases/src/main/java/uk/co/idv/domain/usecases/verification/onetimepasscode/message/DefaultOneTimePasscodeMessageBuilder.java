package uk.co.idv.domain.usecases.verification.onetimepasscode.message;

import uk.co.idv.domain.entities.activity.Activity;

public class DefaultOneTimePasscodeMessageBuilder implements OneTimePasscodeMessageBuilder {

    private static final String TEMPLATE = "Your verification code is %s";

    @Override
    public boolean supports(final String activityName) {
        return true;
    }

    @Override
    public String build(final Activity activity, final String passcode) {
        return String.format(TEMPLATE, passcode);
    }

}
