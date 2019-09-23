package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Optional;

@ToString
@EqualsAndHashCode
public class Eligible implements Eligibility {

    @Override
    public boolean isEligible() {
        return true;
    }

    @Override
    public Optional<String> getReason() {
        return Optional.empty();
    }

}
