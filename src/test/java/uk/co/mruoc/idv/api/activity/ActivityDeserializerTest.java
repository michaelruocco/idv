package uk.co.mruoc.idv.api.activity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.api.IdvModule;
import uk.co.mruoc.idv.api.activity.ActivityDeserializer.ActivityNotSupportedException;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.activity.FakeOnlinePurchase;

import java.io.IOException;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ActivityDeserializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldDeserializeOnlinePurchase() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("activity/online-purchase.json");

        final Activity activity = MAPPER.readValue(json, Activity.class);

        assertThatJson(activity).isEqualTo(new FakeOnlinePurchase());
    }

    @Test
    void shouldThrowExceptionIfActivitylNotSupported() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("activity/not-supported-activity.json");

        final Throwable error = catchThrowable(() -> MAPPER.readValue(json, Activity.class));

        assertThat(error)
                .isInstanceOf(ActivityNotSupportedException.class)
                .hasMessage("not-supported-activity");
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new IdvModule());
        return mapper;
    }

}
