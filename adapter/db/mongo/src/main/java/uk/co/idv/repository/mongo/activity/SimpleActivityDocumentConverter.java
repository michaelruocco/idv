package uk.co.idv.repository.mongo.activity;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.SimpleActivity;

import java.time.Instant;

@RequiredArgsConstructor
public class SimpleActivityDocumentConverter implements ActivityDocumentConverter {

    @Override
    public boolean supportsActivity(final String activityName) {
        return true;
    }

    @Override
    public Activity toActivity(final ActivityDocument document) {
        return SimpleActivity.builder()
                .name(document.getName())
                .timestamp(Instant.parse(document.getTimestamp()))
                .build();
    }

    @Override
    public ActivityDocument toDocument(final Activity activity) {
        final ActivityDocument document = new ActivityDocument();
        document.setName(activity.getName());
        document.setTimestamp(activity.getTimestamp().toString());
        return document;
    }

}
