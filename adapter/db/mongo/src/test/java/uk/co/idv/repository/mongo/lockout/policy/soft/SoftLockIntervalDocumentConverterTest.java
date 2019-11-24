package uk.co.idv.repository.mongo.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervalMother;

import static org.assertj.core.api.Assertions.assertThat;

class SoftLockIntervalDocumentConverterTest {

    private final SoftLockIntervalDocumentConverter converter = new SoftLockIntervalDocumentConverter();

    @Test
    void shouldConvertNumberOfAttemptsToInterval() {
        final SoftLockIntervalDocument document = SoftLockIntervalDocumentMother.oneAttempt();

        final SoftLockInterval interval = converter.toInterval(document);

        assertThat(interval.getNumberOfAttempts()).isEqualTo(document.getNumberOfAttempts());
    }

    @Test
    void shouldConvertDurationToInterval() {
        final SoftLockIntervalDocument document = SoftLockIntervalDocumentMother.oneAttempt();

        final SoftLockInterval interval = converter.toInterval(document);

        assertThat(interval.getDuration().toMillis()).isEqualTo(document.getDuration());
    }

    @Test
    void shouldConvertNumberOfAttemptsToDocument() {
        final SoftLockInterval interval = SoftLockIntervalMother.oneAttempt();

        final SoftLockIntervalDocument document  = converter.toDocument(interval);

        assertThat(document.getNumberOfAttempts()).isEqualTo(interval.getNumberOfAttempts());
    }

    @Test
    void shouldConvertDurationToDocument() {
        final SoftLockInterval interval = SoftLockIntervalMother.oneAttempt();

        final SoftLockIntervalDocument document  = converter.toDocument(interval);

        assertThat(document.getDuration()).isEqualTo(interval.getDuration().toMillis());
    }

}
