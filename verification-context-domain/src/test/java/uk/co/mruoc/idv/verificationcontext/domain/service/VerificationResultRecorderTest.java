package uk.co.mruoc.idv.verificationcontext.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.service.FakeLockoutService;
import uk.co.mruoc.idv.lockout.domain.service.RecordAttemptRequest;
import uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerificationResultRecorderTest {

    private final FakeVerificationContextLoader contextLoader = new FakeVerificationContextLoader();
    private final FakeLockoutService lockoutService = new FakeLockoutService();
    private final VerificationContextDao dao = mock(VerificationContextDao.class);

    private final VerificationContextResultRecorder resultRecorder = VerificationContextResultRecorder.builder()
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
        final VerificationContext contextWithResult = mock(VerificationContext.class);
        given(context.addResult(result)).willReturn(contextWithResult);
        contextLoader.setContextToLoad(context);

        resultRecorder.recordResult(request);

        final LoadContextRequest loadContextRequest = contextLoader.getLastLoadContextRequest();
        assertThat(loadContextRequest.getId()).isEqualTo(contextId);
    }

    @Test
    void shouldPassResultWhenRecordingAttempt() {
        final UUID contextId = UUID.randomUUID();
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");
        final RecordResultRequest request = toRecordResultRequest(contextId, result);

        final VerificationContext context = mock(VerificationContext.class);
        final VerificationContext contextWithResult = mock(VerificationContext.class);
        given(context.addResult(result)).willReturn(contextWithResult);
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
        final VerificationContext contextWithResult = mock(VerificationContext.class);
        given(context.addResult(result)).willReturn(contextWithResult);
        contextLoader.setContextToLoad(context);

        resultRecorder.recordResult(request);

        final RecordAttemptRequest recordAttemptRequest = lockoutService.getLastRecordAttemptRequest();
        assertThat(recordAttemptRequest.getContext()).isEqualTo(contextWithResult);
    }

    @Test
    void shouldSaveContextWithResult() {
        final UUID contextId = UUID.randomUUID();
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");
        final RecordResultRequest request = toRecordResultRequest(contextId, result);

        final VerificationContext context = mock(VerificationContext.class);
        final VerificationContext contextWithResult = mock(VerificationContext.class);
        given(context.addResult(result)).willReturn(contextWithResult);
        contextLoader.setContextToLoad(context);

        resultRecorder.recordResult(request);

        verify(dao).save(contextWithResult);
    }

    @Test
    void shouldReturnContextWithResult() {
        final UUID contextId = UUID.randomUUID();
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");
        final RecordResultRequest request = toRecordResultRequest(contextId, result);

        final VerificationContext context = mock(VerificationContext.class);
        final VerificationContext contextWithResult = mock(VerificationContext.class);
        given(context.addResult(result)).willReturn(contextWithResult);
        contextLoader.setContextToLoad(context);

        final VerificationContext updatedContext = resultRecorder.recordResult(request);

        assertThat(updatedContext).isEqualTo(contextWithResult);
    }

    private static RecordResultRequest toRecordResultRequest(final UUID contextId, final VerificationResult result) {
        return RecordResultRequest.builder()
                .contextId(contextId)
                .result(result)
                .build();
    }

}
