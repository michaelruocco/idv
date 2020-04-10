package uk.co.idv.domain.usecases.verificationcontext;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultVerificationContextServiceTest {

    private final VerificationContextCreator creator = mock(VerificationContextCreator.class);
    private final VerificationContextLoader loader = mock(VerificationContextLoader.class);
    private final VerificationContextResultRecorder resultRecorder = mock(VerificationContextResultRecorder.class);

    private final VerificationContextService contextService = DefaultVerificationContextService.builder()
            .creator(creator)
            .loader(loader)
            .resultRecorder(resultRecorder)
            .build();

    @Test
    void shouldCreateContext() {
        final CreateContextRequest request = CreateContextRequest.builder().build();
        final VerificationContext context = mock(VerificationContext.class);
        given(creator.create(request)).willReturn(context);

        final VerificationContext createdContext = contextService.create(request);

        assertThat(createdContext).isEqualTo(context);
    }

    @Test
    void shouldLoadContext() {
        final UUID id = UUID.randomUUID();
        final VerificationContext context = mock(VerificationContext.class);
        given(loader.load(id)).willReturn(context);

        final VerificationContext loadedContext = contextService.load(id);

        assertThat(loadedContext).isEqualTo(context);
    }

    @Test
    void shouldRecordResults() {
        final RecordResultRequest request = RecordResultRequest.builder().build();
        final VerificationContext context = mock(VerificationContext.class);
        given(resultRecorder.recordResult(request)).willReturn(context);

        final VerificationContext updatedContext = contextService.recordResult(request);

        assertThat(updatedContext).isEqualTo(context);
    }

}
