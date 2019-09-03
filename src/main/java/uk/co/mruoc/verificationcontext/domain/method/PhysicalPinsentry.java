package uk.co.mruoc.verificationcontext.domain.method;

import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class PhysicalPinsentry implements VerificationMethod {

    private final Eligibility eligibility;
    private final PinsentryFunction function;
    private final Collection<CardNumber> cardNumbers;

    @Override
    public String getName() {
        return Names.PHYSICAL_PINSENTRY;
    }

    @Override
    public Duration getDuration() {
        return Duration.ofMinutes(5);
    }

    @Override
    public boolean isEligible() {
        return eligibility.isEligible();
    }

    @Override
    public Eligibility getEligibility() {
        return eligibility;
    }

    public PinsentryFunction getFunction() {
        return function;
    }

    public Collection<CardNumber> getCardNumbers() {
        return Collections.unmodifiableCollection(cardNumbers);
    }

}
