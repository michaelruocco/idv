package uk.co.mruoc.idv.lockout.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.service.RecordAttemptRequest;

import static org.assertj.core.api.Assertions.assertThat;

class RecordNeverTest {

    private final RecordAttemptStrategy strategy = new RecordNever();

    @Test
    void shouldReturnType() {
        assertThat(strategy.getType()).isEqualTo(RecordNever.TYPE);
    }

    @Test
    void shouldAlwaysReturnFalse() {
        final RecordAttemptRequest request = RecordAttemptRequest.builder().build();

        final boolean result = strategy.shouldRecordAttempt(request);

        assertThat(result).isFalse();
    }

}
