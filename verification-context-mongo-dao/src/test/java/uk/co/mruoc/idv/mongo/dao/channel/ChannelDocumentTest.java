package uk.co.mruoc.idv.mongo.dao.channel;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class ChannelDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(ChannelDocument.class);
    }

}
