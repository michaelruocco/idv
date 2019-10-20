package uk.co.mruoc.idv.mongo.dao.activity;

import uk.co.mruoc.idv.domain.exception.ActivityNotSupportedException;
import uk.co.mruoc.idv.domain.model.activity.Activity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class ActivityConverterDelegator {

    private final Collection<ActivityConverter> converters;

    public ActivityConverterDelegator(final ActivityConverter... converters) {
        this(Arrays.asList(converters));
    }

    public ActivityConverterDelegator(final Collection<ActivityConverter> converters) {
        this.converters = converters;
    }

    public Activity toActivity(final ActivityDocument document) {
        final String name = document.getName();
        final Optional<ActivityConverter> activityConverter = findConverter(name);
        return activityConverter
                .map(converter -> converter.toActivity(document))
                .orElseThrow(() -> new ActivityNotSupportedException(name));
    }

    public ActivityDocument toDocument(final Activity activity) {
        final String name = activity.getName();
        final Optional<ActivityConverter> activityConverter = findConverter(name);
        return activityConverter
                .map(converter -> converter.toDocument(activity))
                .orElseThrow(() -> new ActivityNotSupportedException(name));
    }

    private Optional<ActivityConverter> findConverter(final String activityName) {
        return converters.stream()
                .filter(converter -> converter.supportsActivity(activityName))
                .findFirst();
    }

}
