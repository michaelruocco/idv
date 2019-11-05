package uk.co.mruoc.idv.api.lockout;


import uk.co.mruoc.idv.lockout.domain.model.LockoutRequestMother;

public class FakeResetLockoutStateDocument extends ResetLockoutStateDocument {

    FakeResetLockoutStateDocument() {
        super(LockoutRequestMother.fake());
    }

}
