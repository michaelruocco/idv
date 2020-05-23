package uk.co.idv.domain.usecases.verificationcontext.result;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.usecases.lockout.FakeLockoutService;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.usecases.verificationcontext.FakeVerificationContextLoader;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class VerificationResultRecorderTest {

    private final FakeVerificationContextLoader contextLoader = new FakeVerificationContextLoader();
    private final FakeLockoutService lockoutService = new FakeLockoutService();
    private final VerificationContextDao dao = mock(VerificationContextDao.class);

    private final VerificationContextResultRecorder resultRecorder = DefaultVerificationContextResultRecorder.builder()
            .contextLoader(contextLoader)
            .lockoutService(lockoutService)
            .dao(dao)
            .build();

    @Test
    void shouldPassContextIdWhenLoadingContext() {
        final UUID contextId = UUID.randomUUID();
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");
        final RecordResultRequest request = toRecordResultRequest(contextId, result);

        final VerificationContext context = mock(VerificationContext.class);
        contextLoader.setContextToLoad(context);

        resultRecorder.recordResult(request);

        assertThat(contextLoader.getLastLoadedContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldPassResultWhenRecordingAttempt() {
        final UUID contextId = UUID.randomUUID();
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");
        final RecordResultRequest request = toRecordResultRequest(contextId, result);

        final VerificationContext context = mock(VerificationContext.class);
        contextLoader.setContextToLoad(context);

        resultRecorder.recordResult(request);

        final RecordAttemptRequest recordAttemptRequest = lockoutService.getLastRecordAttemptRequest();
        assertThat(recordAttemptRequest.getResult()).isEqualTo(result);
    }

    @Test
    void shouldPassContextWithResultWhenRecordingAttempt() {
        final UUID contextId = UUID.randomUUID();
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");
        final RecordResultRequest request = toRecordResultRequest(contextId, result);

        final VerificationContext context = mock(VerificationContext.class);
        contextLoader.setContextToLoad(context);

        resultRecorder.recordResult(request);

        final RecordAttemptRequest recordAttemptRequest = lockoutService.getLastRecordAttemptRequest();
        assertThat(recordAttemptRequest.getContext()).isEqualTo(context);
    }

    @Test
    void shouldSaveContextWithResult() {
        final UUID contextId = UUID.randomUUID();
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");
        final RecordResultRequest request = toRecordResultRequest(contextId, result);

        final VerificationContext context = mock(VerificationContext.class);
        contextLoader.setContextToLoad(context);

        resultRecorder.recordResult(request);

        final InOrder inOrder = Mockito.inOrder(context, dao);
        inOrder.verify(context).addResult(result);
        inOrder.verify(dao).save(context);
    }

    @Test
    void shouldReturnContextWithResult() {
        final UUID contextId = UUID.randomUUID();
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");
        final RecordResultRequest request = toRecordResultRequest(contextId, result);

        final VerificationContext context = mock(VerificationContext.class);
        contextLoader.setContextToLoad(context);

        final VerificationContext updatedContext = resultRecorder.recordResult(request);

        assertThat(updatedContext).isEqualTo(context);
    }

    private static RecordResultRequest toRecordResultRequest(final UUID contextId, final VerificationResult result) {
        return RecordResultRequest.builder()
                .contextId(contextId)
                .result(result)
                .build();
    }

}
