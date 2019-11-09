package uk.co.idv.repository.mongo.activity;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.exception.ActivityNotSupportedException;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.FakeActivity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ActivityDocumentConverterDelegatorTest {

    private final ActivityDocumentConverter converter = mock(ActivityDocumentConverter.class);
    private final ActivityConverterDelegator delegator = new ActivityConverterDelegator(converter);

    @Test
    void shouldThrowExceptionIfNoConverterSupportingActivityDocument() {
        final ActivityDocument document = ActivityDocumentMother.fake();

        final Throwable error = catchThrowable(() -> delegator.toActivity(document));

        assertThat(error).isInstanceOf(ActivityNotSupportedException.class);
        assertThat(error.getMessage()).isEqualTo(document.getName());
    }

    @Test
    void shouldThrowExceptionIfNoConverterSupportingActivity() {
        final Activity activity = new FakeActivity();

        final Throwable error = catchThrowable(() -> delegator.toDocument(activity));

        assertThat(error).isInstanceOf(ActivityNotSupportedException.class);
        assertThat(error.getMessage()).isEqualTo(activity.getName());
    }

    @Test
    void shouldConvertActivityDocument() {
        final Activity expectedActivity = new FakeActivity();
        final ActivityDocument document = ActivityDocumentMother.fake();
        given(converter.supportsActivity(document.getName())).willReturn(true);
        given(converter.toActivity(document)).willReturn(expectedActivity);

        final Activity activity = delegator.toActivity(document);

        assertThat(activity).isEqualTo(expectedActivity);
    }

    @Test
    void shouldConvertActivity() {
        final Activity activity = new FakeActivity();
        final ActivityDocument expectedDocument = ActivityDocumentMother.fake();
        given(converter.supportsActivity(activity.getName())).willReturn(true);
        given(converter.toDocument(activity)).willReturn(expectedDocument);

        final ActivityDocument document = delegator.toDocument(activity);

        assertThat(document).isEqualTo(expectedDocument);
    }

}
