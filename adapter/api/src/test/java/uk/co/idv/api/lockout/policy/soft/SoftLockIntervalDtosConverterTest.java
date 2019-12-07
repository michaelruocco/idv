package uk.co.idv.api.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervals;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SoftLockIntervalDtosConverterTest {

    private final SoftLockIntervalDto dto1 = new SoftLockIntervalDto(1, 1000L);
    private final SoftLockIntervalDto dto2 = new SoftLockIntervalDto(2, 2000L);
    private final SoftLockInterval interval1 = new SoftLockInterval(1, Duration.ofSeconds(1));
    private final SoftLockInterval interval2 = new SoftLockInterval(2, Duration.ofSeconds(2));

    private final SoftLockIntervalDtoConverter singleConverter = mock(SoftLockIntervalDtoConverter.class);

    private final SoftLockIntervalDtosConverter converter = new SoftLockIntervalDtosConverter(singleConverter);

    @Test
    void shouldConvertToIntervals() {
        given(singleConverter.toInterval(dto1)).willReturn(interval1);
        given(singleConverter.toInterval(dto2)).willReturn(interval2);

        final SoftLockIntervals intervals = converter.toIntervals(Arrays.asList(dto1, dto2));

        assertThat(intervals).containsExactly(
                interval1,
                interval2
        );
    }

    @Test
    void shouldConvertToDtos() {
        given(singleConverter.toDto(interval1)).willReturn(dto1);
        given(singleConverter.toDto(interval2)).willReturn(dto2);

        final Collection<SoftLockIntervalDto> dtos = converter.toDtos(new SoftLockIntervals(interval1, interval2));

        assertThat(dtos).containsExactly(
                dto1,
                dto2
        );
    }

}
