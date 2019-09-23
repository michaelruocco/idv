package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class PhysicalPinsentry implements VerificationMethod {

    private static final String NAME = "physical-pinsentry";

    private final Eligibility eligibility;
    private final Duration duration;
    private final PinsentryFunction function;
    private final Collection<CardNumber> cardNumbers;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Duration getDuration() {
        return duration;
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
