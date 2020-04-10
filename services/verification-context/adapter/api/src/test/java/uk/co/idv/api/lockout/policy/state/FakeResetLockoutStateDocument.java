package uk.co.idv.api.lockout.policy.state;


import uk.co.idv.api.lockout.state.ResetLockoutStateDocument;
import uk.co.idv.domain.entities.lockout.LockoutRequestMother;

public class FakeResetLockoutStateDocument extends ResetLockoutStateDocument {

    FakeResetLockoutStateDocument() {
        super(LockoutRequestMother.fake());
    }

}
