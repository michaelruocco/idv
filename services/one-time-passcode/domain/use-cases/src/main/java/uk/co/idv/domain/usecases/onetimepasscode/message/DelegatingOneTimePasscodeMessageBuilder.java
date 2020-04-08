package uk.co.idv.domain.usecases.onetimepasscode.message;

import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.usecases.exception.ActivityNotSupportedException;

import java.util.Arrays;
import java.util.Collection;

public class DelegatingOneTimePasscodeMessageBuilder implements OneTimePasscodeMessageBuilder {

    private final Collection<OneTimePasscodeMessageBuilder> builders;

    public DelegatingOneTimePasscodeMessageBuilder(final OneTimePasscodeMessageBuilder... builders) {
        this(Arrays.asList(builders));
    }

    public DelegatingOneTimePasscodeMessageBuilder(final Collection<OneTimePasscodeMessageBuilder> builders) {
        this.builders = builders;
    }

    @Override
    public boolean supports(final String activityName) {
        return builders.stream().anyMatch(builder -> builder.supports(activityName));
    }

    @Override
    public String build(final Activity activity, final String passcode) {
        return builders.stream()
                .filter(builder -> builder.supports(activity.getName()))
                .findFirst()
                .map(builder -> builder.build(activity, passcode))
                .orElseThrow(() -> new ActivityNotSupportedException(activity.getName()));
    }

}
