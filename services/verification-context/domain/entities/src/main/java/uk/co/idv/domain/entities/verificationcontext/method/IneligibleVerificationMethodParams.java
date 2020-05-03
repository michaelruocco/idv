package uk.co.idv.domain.entities.verificationcontext.method;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Duration;

@Getter
@EqualsAndHashCode(callSuper = true)
public class IneligibleVerificationMethodParams extends DefaultVerificationMethodParams {

    public IneligibleVerificationMethodParams() {
        super(0, Duration.ZERO);
    }

}
