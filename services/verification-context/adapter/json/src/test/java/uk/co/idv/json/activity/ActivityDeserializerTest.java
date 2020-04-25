package uk.co.idv.json.activity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.ActivityMother;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new ActivityModule()).build();

    @Test
    void shouldDeserializeFakeActivity() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("activity/fake-activity.json");

        final Activity activity = MAPPER.readValue(json, Activity.class);

        assertThat(activity).isEqualToComparingFieldByField(ActivityMother.fake());
    }

    @Test
    void shouldDeserializeLogin() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("activity/login.json");

        final Activity activity = MAPPER.readValue(json, Activity.class);

        assertThat(activity).isEqualToComparingFieldByField(ActivityMother.login());
    }

    @Test
    void shouldDeserializeOnlinePurchase() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("activity/online-purchase.json");

        final Activity activity = MAPPER.readValue(json, Activity.class);

        assertThat(activity).isEqualToComparingFieldByField(ActivityMother.onlinePurchase());
    }

}
