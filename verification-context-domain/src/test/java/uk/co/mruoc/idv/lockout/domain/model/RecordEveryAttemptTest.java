package uk.co.mruoc.idv.lockout.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.service.RecordAttemptRequest;

import static org.assertj.core.api.Assertions.assertThat;

class RecordEveryAttemptTest {

    private final RecordAttemptStrategy strategy = new RecordEveryAttempt();

    @Test
    void shouldReturnType() {
        assertThat(strategy.getType()).isEqualTo(RecordEveryAttempt.TYPE);
    }

    @Test
    void shouldAlwaysReturnTrue() {
        final RecordAttemptRequest request = RecordAttemptRequest.builder().build();

        final boolean result = strategy.shouldRecordAttempt(request);

        assertThat(result).isTrue();
    }

}
