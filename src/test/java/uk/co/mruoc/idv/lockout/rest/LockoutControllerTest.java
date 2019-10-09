package uk.co.mruoc.idv.lockout.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.AliasFactory;
import uk.co.mruoc.idv.identity.domain.model.FakeIdvId;
import uk.co.mruoc.idv.lockout.domain.model.FakeLockoutStateMaxAttempts;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.service.DefaultLockoutRequest;
import uk.co.mruoc.idv.lockout.domain.service.FakeLockoutFacade;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;
import uk.co.mruoc.idv.lockout.jsonapi.LockoutStateDocument;
import uk.co.mruoc.idv.lockout.jsonapi.ResetLockoutStateDocument;

import static org.assertj.core.api.Assertions.assertThat;

class LockoutControllerTest {

    private final String channelId = "channel-id";
    private final String activityName = "activity-name";
    private final Alias alias = new FakeIdvId();

    private final AliasFactory aliasFactory = new AliasFactory();
    private final FakeLockoutFacade facade = new FakeLockoutFacade();
    private final LockoutState state = new FakeLockoutStateMaxAttempts();

    private final LockoutController controller = new LockoutController(aliasFactory, facade);

    @BeforeEach
    void setUp() {
        facade.setStateToReturn(state);
    }

    @Test
    void shouldPassChannelIdWhenLoadingLockoutState() {
        controller.getLockoutState(channelId, activityName, alias.getType(), alias.getValue());

        final LockoutRequest request = facade.getLastGetRequest();
        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldPassActivityNameWhenLoadingLockoutState() {
        controller.getLockoutState(channelId, activityName, alias.getType(), alias.getValue());

        final LockoutRequest request = facade.getLastGetRequest();
        assertThat(request.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldPassAliasWhenLoadingLockoutState() {
        controller.getLockoutState(channelId, activityName, alias.getType(), alias.getValue());

        final LockoutRequest request = facade.getLastGetRequest();
        final Alias requestAlias = request.getAlias();
        assertThat(requestAlias).isEqualTo(alias);
    }

    @Test
    void shouldReturnLockoutStateDocumentWithLockoutState() {
        final LockoutStateDocument document = controller.getLockoutState(channelId, activityName, alias.getType(), alias.getValue());

        assertThat(document.getAttributes()).isEqualTo(state);
    }

    @Test
    void shouldPassLockoutRequestWhenResettingLockoutState() {
        final LockoutRequest request = buildLockoutRequest();
        final ResetLockoutStateDocument resetDocument = toResetDocument(request);

        controller.resetLockoutState(resetDocument);

        final LockoutRequest resetRequest = facade.getLastResetRequest();
        assertThat(resetRequest).isEqualTo(request);
    }

    @Test
    void shouldReturnResetLockoutStateDocumentWithLockoutState() {
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
