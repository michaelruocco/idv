package uk.co.idv.repository.mongo.beantest;

import org.meanbean.factories.FactoryCollection;
import org.meanbean.test.BeanTester;
import uk.co.idv.repository.mongo.identity.alias.AliasDocument;
import uk.co.idv.repository.mongo.identity.alias.AliasDocumentFactory;

import java.math.BigDecimal;

public class DocumentBeanTester extends BeanTester {

    public DocumentBeanTester() {
        final FactoryCollection factories = getFactoryCollection();
        factories.addFactory(BigDecimal.class, new BigDecimalFactory());
        factories.addFactory(AliasDocument.class, new AliasDocumentFactory());
    }

}
