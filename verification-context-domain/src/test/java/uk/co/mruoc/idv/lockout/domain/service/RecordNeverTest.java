package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RecordNeverTest {

    private final RecordAttemptStrategy strategy = new RecordNever();

    @Test
    void shouldAlwaysReturnFalse() {
        final RecordAttemptRequest request = RecordAttemptRequest.builder().build();

        final boolean result = strategy.shouldRecordAttempt(request);

        assertThat(result).isFalse();
    }

}
