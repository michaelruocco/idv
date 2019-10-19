package uk.co.mruoc.idv.mongo.dao.activity;

import uk.co.mruoc.idv.domain.model.activity.Activity;

public interface ActivityConverter {

    String getSupportedActivityName();

    Activity toActivity(final ActivityDocument document);

    ActivityDocument toDocument(final Activity activity);

}
