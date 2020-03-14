package uk.co.idv.domain.usecases.verification.onetimepasscode.message;

import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.OnlinePurchase;

public class OnlinePurchaseOneTimePasscodeMessageBuilder implements OneTimePasscodeMessageBuilder {

    private static final String TEMPLATE = "Your verification code for your purchase from %s using card number %s for amount %s is %s";

    @Override
    public boolean supports(final String activityName) {
        return OnlinePurchase.NAME.equals(activityName);
    }

    @Override
    public String build(final Activity activity, final String passcode) {
        final OnlinePurchase onlinePurchase = (OnlinePurchase) activity;
        return String.format(TEMPLATE,
                onlinePurchase.getMerchantName(),
                onlinePurchase.getTokenizedCardNumber(),
                onlinePurchase.getCost(),
                passcode);
    }

}
