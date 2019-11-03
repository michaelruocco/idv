package uk.co.mruoc.idv.lockout.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.model.RecordAttemptStrategyFactory.RecordAttemptStrategyNotSupportedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class RecordAttemptStrategyFactoryTest {

    private final RecordAttemptStrategyFactory factory = new RecordAttemptStrategyFactory();

    @Test
    void shouldThrowExceptionIfTypeIsInvalid() {
        final String type = "invalid";

        final Throwable error = catchThrowable(() -> factory.build(type));

        assertThat(error).isInstanceOf(RecordAttemptStrategyNotSupportedException.class);
    }

    @Test
    void shouldPopulateTypeOnExceptionIfTypeIsInvalid() {
        final String type = "invalid";

        final Throwable error = catchThrowable(() -> factory.build(type));

        assertThat(error).hasMessage(type);
    }

    @Test
    void shouldReturnRecordEveryAttempt() {
        final String type = RecordEveryAttempt.TYPE;

        final RecordAttemptStrategy strategy = factory.build(type);

        assertThat(strategy).isInstanceOf(RecordEveryAttempt.class);
    }

    @Test
    void shouldReturnRecordNever() {
        final String type = RecordNever.TYPE;

        final RecordAttemptStrategy strategy = factory.build(type);

        assertThat(strategy).isInstanceOf(RecordNever.class);
    }

    @Test
    void shouldReturnRecordOnMethodComplete() {
        final String type = RecordOnMethodComplete.TYPE;

        final RecordAttemptStrategy strategy = factory.build(type);

        assertThat(strategy).isInstanceOf(RecordOnMethodComplete.class);
    }

    @Test
    void shouldReturnRecordOnSequenceComplete() {
        final String type = RecordOnSequenceComplete.TYPE;

        final RecordAttemptStrategy strategy = factory.build(type);

        assertThat(strategy).isInstanceOf(RecordOnSequenceComplete.class);
    }

}
