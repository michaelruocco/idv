package uk.co.idv.api.lockout.policy.state;


import uk.co.idv.api.lockout.state.LockoutStateDocument;
import uk.co.idv.domain.entities.lockout.policy.hard.FakeHardLockoutState;

public class FakeLockoutStateDocument extends LockoutStateDocument {

    FakeLockoutStateDocument() {
        super(new FakeHardLockoutState());
    }

}
