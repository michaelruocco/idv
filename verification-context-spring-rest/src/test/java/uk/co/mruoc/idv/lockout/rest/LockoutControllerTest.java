package uk.co.mruoc.idv.lockout.rest;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.AliasFactory;
import uk.co.mruoc.idv.identity.domain.model.IdvId;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.service.DefaultLockoutRequest;
import uk.co.mruoc.idv.lockout.domain.service.LockoutFacade;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;
import uk.co.mruoc.idv.lockout.jsonapi.LockoutStateDocument;
import uk.co.mruoc.idv.lockout.jsonapi.ResetLockoutStateDocument;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LockoutControllerTest {

    private final String channelId = "channel-id";
    private final String activityName = "activity-name";
    private final Alias alias = new IdvId(UUID.randomUUID());

    private final AliasFactory aliasFactory = new AliasFactory();
    private final LockoutFacade facade = mock(LockoutFacade.class);
    private final LockoutState state = mock(LockoutState.class);

    private final LockoutController controller = new LockoutController(aliasFactory, facade);

    @Test
    void shouldPassChannelIdWhenLoadingLockoutState() {
        given(facade.loadLockoutState(any(LockoutRequest.class))).willReturn(state);

        controller.getLockoutState(channelId, activityName, alias.getType(), alias.getValue());

        final ArgumentCaptor<LockoutRequest> captor = ArgumentCaptor.forClass(LockoutRequest.class);
        verify(facade).loadLockoutState(captor.capture());
        final LockoutRequest request = captor.getValue();
        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldPassActivityNameWhenLoadingLockoutState() {
        given(facade.loadLockoutState(any(LockoutRequest.class))).willReturn(state);

        controller.getLockoutState(channelId, activityName, alias.getType(), alias.getValue());

        final ArgumentCaptor<LockoutRequest> captor = ArgumentCaptor.forClass(LockoutRequest.class);
        verify(facade).loadLockoutState(captor.capture());
        final LockoutRequest request = captor.getValue();
        assertThat(request.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldPassAliasWhenLoadingLockoutState() {
        given(facade.loadLockoutState(any(LockoutRequest.class))).willReturn(state);

        controller.getLockoutState(channelId, activityName, alias.getType(), alias.getValue());

        final ArgumentCaptor<LockoutRequest> captor = ArgumentCaptor.forClass(LockoutRequest.class);
        verify(facade).loadLockoutState(captor.capture());
        final LockoutRequest request = captor.getValue();
        final Alias requestAlias = request.getAlias();
        assertThat(requestAlias).isEqualTo(alias);
    }

    @Test
    void shouldReturnLockoutStateDocumentWithLockoutState() {
        given(facade.loadLockoutState(any(LockoutRequest.class))).willReturn(state);

        final LockoutStateDocument document = controller.getLockoutState(channelId, activityName, alias.getType(), alias.getValue());

        assertThat(document.getAttributes()).isEqualTo(state);
    }

    @Test
    void shouldPassLockoutRequestWhenResettingLockoutState() {
        given(facade.resetLockoutState(any(LockoutRequest.class))).willReturn(state);
        final LockoutRequest request = buildLockoutRequest();
        final ResetLockoutStateDocument resetDocument = toResetDocument(request);

        controller.resetLockoutState(resetDocument);

        final ArgumentCaptor<LockoutRequest> captor = ArgumentCaptor.forClass(LockoutRequest.class);
        verify(facade).resetLockoutState(captor.capture());
        final LockoutRequest resetRequest = captor.getValue();
        assertThat(resetRequest).isEqualTo(request);
    }

    @Test
    void shouldReturnResetLockoutStateDocumentWithLockoutState() {
        given(facade.resetLockoutState(any(LockoutRequest.class))).willReturn(state);
        final LockoutRequest request = buildLockoutRequest();
        final ResetLockoutStateDocument resetDocument = toResetDocument(request);

        final LockoutStateDocument stateDocument = controller.resetLockoutState(resetDocument);

        assertThat(stateDocument.getAttributes()).isEqualTo(state);
    }

    private LockoutRequest buildLockoutRequest() {
        return DefaultLockoutRequest.builder()
                .channelId(channelId)
                .activityName(activityName)
                .alias(alias)
                .build();
    }

    private static ResetLockoutStateDocument toResetDocument(final LockoutRequest request) {
        return new ResetLockoutStateDocument(request);
    }

}
