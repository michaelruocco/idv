package uk.co.idv.json.mobiledevice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.mobiledevice.MobileDeviceMother;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

class MobileDeviceDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new MobileDeviceModule()).build();

    @Test
    void shouldDeserializePhoneNumbers() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("mobile-device/mobile-device.json");

        final MobileDevice device = MAPPER.readValue(json, MobileDevice.class);

        assertThat(device).isEqualTo(MobileDeviceMother.trusted());
    }

}
