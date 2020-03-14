package uk.co.idv.json.activity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.ActivityMother;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class ActivityMixinTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldSerializeOnlinePurchase() throws JsonProcessingException {
        final Activity activity = ActivityMother.onlinePurchase();

        final String json = MAPPER.writeValueAsString(activity);

        final String expectedJson = ContentLoader.loadContentFromClasspath("activity/online-purchase.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
