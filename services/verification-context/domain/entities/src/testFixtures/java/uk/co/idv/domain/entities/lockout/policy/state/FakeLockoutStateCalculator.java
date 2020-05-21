package uk.co.idv.domain.entities.lockout.policy.state;


public class FakeLockoutStateCalculator implements LockoutStateCalculator {

    private CalculateLockoutStateRequest lastCalculateStateRequest;
    private LockoutState stateToReturn;

    @Override
    public String getType() {
        return "fake-lockout-type";
    }

    @Override
    public LockoutState calculate(final CalculateLockoutStateRequest request) {
        this.lastCalculateStateRequest = request;
        return stateToReturn;
    }

    public CalculateLockoutStateRequest getLastCalculateStateRequest() {
        return lastCalculateStateRequest;
    }

    public void setLockoutStateToReturn(final LockoutState stateToReturn) {
        this.stateToReturn = stateToReturn;
    }

}
