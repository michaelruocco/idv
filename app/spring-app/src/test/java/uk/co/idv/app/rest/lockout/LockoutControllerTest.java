package uk.co.idv.app.rest.lockout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.DefaultLockoutRequest;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.usecases.lockout.FakeLockoutFacade;
import uk.co.mruoc.idv.api.lockout.LockoutStateDocument;
import uk.co.mruoc.idv.api.lockout.ResetLockoutStateDocument;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasFactory;
import uk.co.idv.domain.entities.identity.alias.IdvId;
import uk.co.idv.domain.entities.lockout.state.FakeLockoutStateMaxAttempts;
import uk.co.idv.domain.entities.lockout.state.LockoutState;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LockoutControllerTest {

    private final String channelId = "channel-id";
    private final String activityName = "activity-name";
    private final Alias alias = new IdvId(UUID.randomUUID());

    private final FakeLockoutFacade facade = new FakeLockoutFacade();
    private final LockoutState state = new FakeLockoutStateMaxAttempts();

    private final AliasFactory aliasFactory = new AliasFactory();
    private final LockoutController controller = new LockoutController(aliasFactory, facade);

    @BeforeEach
    void setUp() {
        facade.setStateToReturn(state);
    }

    @Test
    void shouldPassChannelIdWhenLoadingLockoutState() {
        controller.getLockoutState(
                channelId,
                activityName,
                alias.getType(),
                alias.getValue()
        );

        final LockoutRequest request = facade.getLastLoadRequest();
        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldPassActivityNameWhenLoadingLockoutState() {
        controller.getLockoutState(
                channelId,
                activityName,
                alias.getType(),
                alias.getValue()
        );

        final LockoutRequest request = facade.getLastLoadRequest();
        assertThat(request.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldPassAliasWhenLoadingLockoutState() {
        controller.getLockoutState(
                channelId,
                activityName,
                alias.getType(),
                alias.getValue()
        );

        final LockoutRequest request = facade.getLastLoadRequest();
        assertThat(request.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldReturnLockoutStateDocumentWithLockoutState() {
        final LockoutStateDocument document = controller.getLockoutState(
                channelId,
                activityName,
                alias.getType(),
                alias.getValue()
        );

        assertThat(document.getAttributes()).isEqualTo(state);
    }

    @Test
    void shouldPassLockoutRequestWhenResettingLockoutState() {
        final ResetLockoutStateDocument resetDocument = buildResetDocument();

        controller.resetLockoutState(resetDocument);

        final LockoutRequest request = facade.getLastResetRequest();
        assertThat(request).isEqualTo(resetDocument.getAttributes());
    }

    @Test
    void shouldReturnResetLockoutStateDocumentWithLockoutState() {
        final ResetLockoutStateDocument resetDocument = buildResetDocument();

        final LockoutStateDocument stateDocument = controller.resetLockoutState(resetDocument);

        assertThat(stateDocument.getAttributes()).isEqualTo(state);
    }

    private ResetLockoutStateDocument buildResetDocument() {
        final LockoutRequest request = buildLockoutRequest();
        return new ResetLockoutStateDocument(request);
    }

    private LockoutRequest buildLockoutRequest() {
        return DefaultLockoutRequest.builder()
                .channelId(channelId)
                .activityName(activityName)
                .alias(alias)
                .build();
    }

}
