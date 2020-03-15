package uk.co.idv.domain.usecases.verification.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerificationMother;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.usecases.util.FakeTimeGenerator;
import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.idv.domain.usecases.verificationcontext.RecordResultRequest;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeVerificationConverterTest {

    private final Instant now = Instant.now();
    private final TimeGenerator timeGenerator = new FakeTimeGenerator(now);

    private final OneTimePasscodeVerificationConverter converter = new OneTimePasscodeVerificationConverter(timeGenerator);

    @Test
    void shouldPopulateContextIdOnRequest() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();

        final RecordResultRequest request = converter.toRecordResultRequest(verification);

        assertThat(request.getContextId()).isEqualTo(verification.getContextId());
    }

    @Test
    void shouldPopulateOneTimePasscodeVerificationResult() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();

        final RecordResultRequest request = converter.toRecordResultRequest(verification);

        assertThat(request.getResult()).isInstanceOf(OneTimePasscodeVerificationResult.class);
    }

    @Test
    void shouldPopulateVerificationIdOnResult() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();

        final RecordResultRequest request = converter.toRecordResultRequest(verification);

        final VerificationResult result = request.getResult();
        assertThat(result.getVerificationId()).isEqualTo(verification.getId());
    }

    @Test
    void shouldPopulateTimestampOnResult() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();

        final RecordResultRequest request = converter.toRecordResultRequest(verification);

        final VerificationResult result = request.getResult();
        assertThat(result.getTimestamp()).isEqualTo(now);
    }

    @Test
    void shouldPopulateSuccessfulOnResult() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();

        final RecordResultRequest request = converter.toRecordResultRequest(verification);

        final VerificationResult result = request.getResult();
        assertThat(result.isSuccessful()).isEqualTo(verification.isSuccessful());
    }

}
