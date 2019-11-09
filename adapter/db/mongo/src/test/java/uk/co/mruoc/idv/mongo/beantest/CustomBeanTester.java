package uk.co.mruoc.idv.mongo.beantest;

import org.meanbean.factories.FactoryCollection;
import org.meanbean.test.BeanTester;
import uk.co.mruoc.idv.mongo.dao.activity.ActivityDocument;
import uk.co.mruoc.idv.mongo.dao.activity.MonetaryAmountDocument;
import uk.co.mruoc.idv.mongo.dao.channel.ChannelDocument;
import uk.co.mruoc.idv.mongo.identity.dao.AliasDocument;
import uk.co.mruoc.idv.mongo.identity.dao.IdentityDocument;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.eligibility.EligibilityDocument;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.PasscodeSettingsDocument;

import java.math.BigDecimal;

public class CustomBeanTester extends BeanTester {

    public CustomBeanTester() {
        final FactoryCollection factories = getFactoryCollection();
        factories.addFactory(BigDecimal.class, new BigDecimalFactory());
        factories.addFactory(MonetaryAmountDocument.class, new MonetaryAmountDocumentFactory());
        factories.addFactory(ChannelDocument.class, new ChannelDocumentFactory());
        factories.addFactory(AliasDocument.class, new AliasDocumentFactory());
        factories.addFactory(ActivityDocument.class, new ActivityDocumentFactory());
        factories.addFactory(EligibilityDocument.class, new EligibilityDocumentFactory());
        factories.addFactory(PasscodeSettingsDocument.class, new PasscodeSettingsDocumentFactory());
        factories.addFactory(IdentityDocument.class, new IdentityDocumentFactory());
    }

}
