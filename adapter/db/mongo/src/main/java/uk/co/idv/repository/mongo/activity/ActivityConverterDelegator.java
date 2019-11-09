package uk.co.idv.repository.mongo.activity;

import uk.co.mruoc.idv.domain.exception.ActivityNotSupportedException;
import uk.co.mruoc.idv.domain.model.activity.Activity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class ActivityConverterDelegator {

    private final Collection<ActivityDocumentConverter> converters;

    public ActivityConverterDelegator(final ActivityDocumentConverter... converters) {
        this(Arrays.asList(converters));
    }

    public ActivityConverterDelegator(final Collection<ActivityDocumentConverter> converters) {
        this.converters = converters;
    }

    public Activity toActivity(final ActivityDocument document) {
        final String name = document.getName();
        final Optional<ActivityDocumentConverter> activityConverter = findConverter(name);
        return activityConverter
                .map(converter -> converter.toActivity(document))
                .orElseThrow(() -> new ActivityNotSupportedException(name));
    }

    public ActivityDocument toDocument(final Activity activity) {
        final String name = activity.getName();
        final Optional<ActivityDocumentConverter> activityConverter = findConverter(name);
        return activityConverter
                .map(converter -> converter.toDocument(activity))
                .orElseThrow(() -> new ActivityNotSupportedException(name));
    }

    private Optional<ActivityDocumentConverter> findConverter(final String activityName) {
        return converters.stream()
                .filter(converter -> converter.supportsActivity(activityName))
                .findFirst();
    }

}
