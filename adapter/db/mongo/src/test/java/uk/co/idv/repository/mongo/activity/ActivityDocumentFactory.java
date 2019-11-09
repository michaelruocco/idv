package uk.co.idv.repository.mongo.activity;

import org.meanbean.lang.Factory;

public class ActivityDocumentFactory implements Factory<ActivityDocument> {

    @Override
    public ActivityDocument create() {
        return ActivityDocumentMother.fake();
    }

}
