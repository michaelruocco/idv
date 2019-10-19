package uk.co.mruoc.idv.mongo.dao.activity;

import uk.co.mruoc.idv.domain.exception.ActivityNotSupportedException;
import uk.co.mruoc.idv.domain.model.activity.Activity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ActivityConverterDelegator {

    private final Map<String, ActivityConverter> converters = new HashMap<>();

    public ActivityConverterDelegator(final Collection<ActivityConverter> converters) {
        converters.forEach(converter -> this.converters.put(converter.getSupportedActivityName(), converter));
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
        return Optional.ofNullable(converters.get(activityName));
    }

}
