package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.model.activity.OnlinePurchase;
import uk.co.mruoc.idv.domain.model.channel.Rsa;
import uk.co.mruoc.idv.identity.domain.model.CreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.DebitCardNumber;
import uk.co.mruoc.idv.lockout.domain.model.FakeVerificationAttempts;
import uk.co.mruoc.idv.lockout.domain.model.LockoutStateMaxAttempts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutPolicyRsaTest {

    private final LockoutPolicyRsa policy = new LockoutPolicyRsa();

    @Test
    void shouldNotApplyIfChannelIsNotRsa() {
        final LockoutRequest request = mock(LockoutRequest.class);
        given(request.getChannelId()).willReturn("channel-id");
        given(request.getActivityName()).willReturn(OnlinePurchase.NAME);
        given(request.getAliasType()).willReturn(CreditCardNumber.TYPE);

        final boolean applies = policy.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyIfActivityNameIsNotOnlinePurchase() {
        final LockoutRequest request = mock(LockoutRequest.class);
        given(request.getChannelId()).willReturn(Rsa.ID);
        given(request.getActivityName()).willReturn("activity-name");
        given(request.getAliasType()).willReturn(CreditCardNumber.TYPE);

        final boolean applies = policy.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyIfAliasTypeIsNotCreditOrDebitCardNumber() {
        final LockoutRequest request = mock(LockoutRequest.class);
        given(request.getChannelId()).willReturn(Rsa.ID);
        given(request.getActivityName()).willReturn(OnlinePurchase.NAME);
        given(request.getAliasType()).willReturn("alias-type");

        final boolean applies = policy.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyIfChannelIdIsRsaActivityNameIsOnlinePurchaseAndAliasTypeIsCreditCardNumber() {
        final LockoutRequest request = mock(LockoutRequest.class);
        given(request.getChannelId()).willReturn(Rsa.ID);
        given(request.getActivityName()).willReturn(OnlinePurchase.NAME);
        given(request.getAliasType()).willReturn(CreditCardNumber.TYPE);

        final boolean applies = policy.appliesTo(request);

        assertThat(applies).isTrue();
    }

    @Test
    void shouldNotApplyIfChannelIdIsRsaActivityNameIsOnlinePurchaseAndAliasTypeIsDebitCardNumber() {
        final LockoutRequest request = mock(LockoutRequest.class);
        given(request.getChannelId()).willReturn(Rsa.ID);
        given(request.getActivityName()).willReturn(OnlinePurchase.NAME);
        given(request.getAliasType()).willReturn(DebitCardNumber.TYPE);

        final boolean applies = policy.appliesTo(request);

        assertThat(applies).isTrue();
    }

    @Test
    void shouldRecordEveryAttempt() {
        final RecordAttemptRequest request = mock(RecordAttemptRequest.class);

        final boolean applies = policy.shouldRecordAttempt(request);

        assertThat(applies).isTrue();
    }

    @Test
    void shouldHaveMaxAttemptsThree() {
        final CalculateLockoutStateRequest request = mock(CalculateLockoutStateRequest.class);
        given(request.getChannelId()).willReturn(Rsa.ID);
        given(request.getActivityName()).willReturn(OnlinePurchase.NAME);
        given(request.getAliasType()).willReturn(DebitCardNumber.TYPE);
        given(request.getAttempts()).willReturn(new FakeVerificationAttempts());

        final LockoutStateMaxAttempts state = (LockoutStateMaxAttempts) policy.calculateLockoutState(request);

        assertThat(state.getMaxNumberOfAttempts()).isEqualTo(3);
    }

}
