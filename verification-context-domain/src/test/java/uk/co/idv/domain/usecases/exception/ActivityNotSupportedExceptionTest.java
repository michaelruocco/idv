package uk.co.idv.domain.usecases.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityNotSupportedExceptionTest {

    @Test
    void shouldReturnMessage() {
        final String activityName = "activity-name";

        final Throwable error = new ActivityNotSupportedException(activityName);

        assertThat(error.getMessage()).isEqualTo(activityName);
    }

}
