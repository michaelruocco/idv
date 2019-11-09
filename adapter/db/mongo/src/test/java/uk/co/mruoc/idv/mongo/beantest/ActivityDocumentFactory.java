package uk.co.mruoc.idv.mongo.beantest;

import org.meanbean.lang.Factory;
import uk.co.mruoc.idv.mongo.dao.activity.ActivityDocument;
import uk.co.mruoc.idv.mongo.dao.activity.ActivityDocumentMother;

public class ActivityDocumentFactory implements Factory<ActivityDocument> {

    @Override
    public ActivityDocument create() {
        return ActivityDocumentMother.fake();
    }

}
