package uk.co.idv.domain.usecases.verification.onetimepasscode;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class VerifyOneTimePasscodeRequestMother {

    public static VerifyOneTimePasscodeRequest build() {
        return build("12345678");
    }

    public static VerifyOneTimePasscodeRequest build(final String... passcodes) {
        return build(Arrays.asList(passcodes));
    }

    public static VerifyOneTimePasscodeRequest build(final Collection<String> passcodes) {
        return VerifyOneTimePasscodeRequest.builder()
                .id(UUID.fromString("c5b9f6f8-c45f-4d0e-a5eb-c5e344024db1"))
                .passcodes(passcodes)
                .build();
    }

}
