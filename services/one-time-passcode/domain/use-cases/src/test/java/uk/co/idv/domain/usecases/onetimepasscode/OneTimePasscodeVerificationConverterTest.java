package uk.co.idv.domain.usecases.onetimepasscode;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationMother;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.usecases.util.FakeTimeProvider;
import uk.co.idv.domain.usecases.util.TimeProvider;
import uk.co.idv.domain.usecases.verificationcontext.RecordResultRequest;

import java.time.Instant;

class OneTimePasscodeVerificationConverterTest {

    private final Instant now = Instant.now();
    private final TimeProvider timeProvider = new FakeTimeProvider(now);

    private final OneTimePasscodeVerificationConverter converter = new OneTimePasscodeVerificationConverter(timeProvider);

    @Test
    void shouldPopulateContextIdOnRequest() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();

        final RecordResultRequest request = converter.toRecordResultRequest(verification);

        Assertions.assertThat(request.getContextId()).isEqualTo(verification.getContextId());
    }

    @Test
    void shouldPopulateOneTimePasscodeVerificationResult() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();

        final RecordResultRequest request = converter.toRecordResultRequest(verification);

        Assertions.assertThat(request.getResult()).isInstanceOf(OneTimePasscodeVerificationResult.class);
    }

    @Test
    void shouldPopulateVerificationIdOnResult() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();

        final RecordResultRequest request = converter.toRecordResultRequest(verification);

        final VerificationResult result = request.getResult();
        Assertions.assertThat(result.getVerificationId()).isEqualTo(verification.getId());
    }

    @Test
    void shouldPopulateTimestampOnResult() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();

        final RecordResultRequest request = converter.toRecordResultRequest(verification);

        final VerificationResult result = request.getResult();
        Assertions.assertThat(result.getTimestamp()).isEqualTo(now);
    }

    @Test
    void shouldPopulateSuccessfulOnResult() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();

        final RecordResultRequest request = converter.toRecordResultRequest(verification);

        final VerificationResult result = request.getResult();
        Assertions.assertThat(result.isSuccessful()).isEqualTo(verification.isSuccessful());
    }

}
