package uk.co.idv.repository.mongo.activity;

import uk.co.mruoc.idv.domain.model.activity.Activity;

public interface ActivityDocumentConverter {

    boolean supportsActivity(final String activityName);

    Activity toActivity(final ActivityDocument document);

    ActivityDocument toDocument(final Activity activity);

}
