package uk.co.idv.repository.mongo.beantest;

import org.meanbean.factories.FactoryCollection;
import org.meanbean.test.BeanTester;
import uk.co.idv.repository.mongo.activity.ActivityDocument;
import uk.co.idv.repository.mongo.activity.ActivityDocumentFactory;
import uk.co.idv.repository.mongo.activity.MonetaryAmountDocument;
import uk.co.idv.repository.mongo.identity.IdentityDocumentFactory;
import uk.co.idv.repository.mongo.identity.alias.AliasDocumentFactory;
import uk.co.idv.repository.mongo.verificationcontext.method.onetimepasscode.PasscodeSettingsDocumentFactory;
import uk.co.idv.repository.mongo.channel.ChannelDocument;
import uk.co.idv.repository.mongo.channel.ChannelDocumentFactory;
import uk.co.idv.repository.mongo.identity.alias.AliasDocument;
import uk.co.idv.repository.mongo.identity.IdentityDocument;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocument;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentFactory;
import uk.co.idv.repository.mongo.verificationcontext.method.onetimepasscode.PasscodeSettingsDocument;

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
