package uk.co.idv.json.activity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.ActivityMother;
import uk.co.idv.domain.usecases.exception.ActivityNotSupportedException;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ActivityDeserializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldDeserializeOnlinePurchase() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("activity/online-purchase.json");

        final Activity activity = MAPPER.readValue(json, Activity.class);

        assertThat(activity).isEqualToComparingFieldByField(ActivityMother.onlinePurchase());
    }

    @Test
    void shouldDeserializeLogin() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("activity/login.json");

        final Activity activity = MAPPER.readValue(json, Activity.class);

        assertThat(activity).isEqualToComparingFieldByField(ActivityMother.login());
    }

    @Test
    void shouldThrowExceptionIfActivityNotSupported() {
        final String json = ContentLoader.loadContentFromClasspath("activity/not-supported-activity.json");

        final Throwable error = catchThrowable(() -> MAPPER.readValue(json, Activity.class));

        assertThat(error)
                .isInstanceOf(ActivityNotSupportedException.class)
                .hasMessage("not-supported-activity");
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ActivityModule());
        return mapper;
    }

}
