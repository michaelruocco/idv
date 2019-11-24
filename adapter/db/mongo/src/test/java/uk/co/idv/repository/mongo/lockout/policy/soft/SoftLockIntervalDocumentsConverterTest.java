package uk.co.idv.repository.mongo.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervalMother;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervals;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SoftLockIntervalDocumentsConverterTest {

    private final SoftLockIntervalDocument document1 = SoftLockIntervalDocumentMother.build(1);
    private final SoftLockIntervalDocument document2 = SoftLockIntervalDocumentMother.build(2);

    private final SoftLockInterval interval1 = SoftLockIntervalMother.build(1);
    private final SoftLockInterval interval2 = SoftLockIntervalMother.build(2);

    private final SoftLockIntervalDocumentConverter documentConverter = mock(SoftLockIntervalDocumentConverter.class);

    private final SoftLockIntervalDocumentsConverter converter = new SoftLockIntervalDocumentsConverter(documentConverter);

    @Test
    void shouldConvertDocuments() {
        given(documentConverter.toInterval(document1)).willReturn(interval1);
        given(documentConverter.toInterval(document2)).willReturn(interval2);

        final SoftLockIntervals intervals = converter.toIntervals(Arrays.asList(document1, document2));

        assertThat(intervals.stream()).containsExactly(interval1, interval2);
    }

    @Test
    void shouldConvertIntervals() {
        given(documentConverter.toDocument(interval1)).willReturn(document1);
        given(documentConverter.toDocument(interval2)).willReturn(document2);
        final SoftLockIntervals intervals = new SoftLockIntervals(Arrays.asList(interval1, interval2));

        final Collection<SoftLockIntervalDocument> documents = converter.toDocuments(intervals);

        assertThat(documents).containsExactly(document1, document2);
    }

}
