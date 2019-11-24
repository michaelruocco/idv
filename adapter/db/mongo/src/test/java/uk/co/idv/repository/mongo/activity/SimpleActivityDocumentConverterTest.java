package uk.co.idv.repository.mongo.activity;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.ActivityMother;
import uk.co.idv.domain.entities.activity.SimpleActivity;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleActivityDocumentConverterTest {

    private final ActivityDocumentConverter converter = new SimpleActivityDocumentConverter();

    @Test
    void shouldOnlySupportAnyActivityName() {
        assertThat(converter.supportsActivity("any-activity")).isTrue();
    }

    @Test
    void shouldConvertToSimpleActivity() {
        final ActivityDocument document = ActivityDocumentMother.fake();

        final Activity activity = converter.toActivity(document);

        assertThat(activity).isInstanceOf(SimpleActivity.class);
    }

    @Test
    void shouldConvertTimestampToActivity() {
        final ActivityDocument document = ActivityDocumentMother.fake();

        final Activity activity = converter.toActivity(document);

        assertThat(activity.getTimestamp()).isEqualTo(Instant.parse(document.getTimestamp()));
    }

    @Test
    void shouldConvertToActivityDocument() {
        final Activity activity = ActivityMother.fake();

        final ActivityDocument document = converter.toDocument(activity);

        assertThat(document).isInstanceOf(ActivityDocument.class);
    }

    @Test
    void shouldConvertNameToDocument() {
        final Activity activity = ActivityMother.fake();

        final ActivityDocument document = converter.toDocument(activity);

        assertThat(document.getName()).isEqualTo(activity.getName());
    }

    @Test
    void shouldConvertTimestampToDocument() {
        final Activity activity = ActivityMother.fake();

        final ActivityDocument document = converter.toDocument(activity);

        assertThat(document.getTimestamp()).isEqualTo(activity.getTimestamp().toString());
    }

}
