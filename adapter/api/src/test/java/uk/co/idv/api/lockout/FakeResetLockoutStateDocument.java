package uk.co.idv.api.lockout;


import uk.co.idv.domain.entities.lockout.LockoutRequestMother;

public class FakeResetLockoutStateDocument extends ResetLockoutStateDocument {

    FakeResetLockoutStateDocument() {
        super(LockoutRequestMother.fake());
    }

}
