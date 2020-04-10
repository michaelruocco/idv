package uk.co.idv.json.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeDeliveryMother;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class OneTimePasscodeDeliveryDeserializerTest {

    private static final ObjectMapper MAPPER = new OneTimePasscodeObjectMapperFactory().build();

    @Test
    void shouldDeserializeDelivery() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("one-time-passcode-delivery.json");

        final OneTimePasscodeDelivery delivery = MAPPER.readValue(json, OneTimePasscodeDelivery.class);

        assertThat(delivery).isEqualToComparingFieldByField(OneTimePasscodeDeliveryMother.smsDelivery());
    }

}
