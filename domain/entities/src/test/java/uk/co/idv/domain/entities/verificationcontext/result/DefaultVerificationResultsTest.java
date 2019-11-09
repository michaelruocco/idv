package uk.co.idv.domain.entities.verificationcontext.result;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class DefaultVerificationResultsTest {

    @Test
    void shouldReturnStream() {
        final VerificationResult result1 = mock(VerificationResult.class);
        final VerificationResult result2 = mock(VerificationResult.class);

        final VerificationResults results = new DefaultVerificationResults(Arrays.asList(result1, result2));

        assertThat(results.stream()).containsExactly(result1, result2);
    }

}
