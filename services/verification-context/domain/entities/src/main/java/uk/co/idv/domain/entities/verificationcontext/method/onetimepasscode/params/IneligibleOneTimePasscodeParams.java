package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params;

import lombok.Getter;

import java.time.Duration;

@Getter
public class IneligibleOneTimePasscodeParams extends DefaultOneTimePasscodeParams {

    public IneligibleOneTimePasscodeParams() {
        super(new IneligiblePasscodeSettings(), 0, Duration.ZERO);
    }

}
