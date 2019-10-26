package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import java.util.UUID;

public class MobileNumberMother {

    private MobileNumberMother() {
        // utility class
    }

    public static MobileNumber primary() {
        return new MobileNumber(
                UUID.fromString("6c880ce6-0d3c-4ac7-b419-8c2dce645cfa"),
                "07819389980"
        );
    }

    public static MobileNumber secondary() {
        return new MobileNumber(
                UUID.fromString("543185d6-0048-484e-8950-52c74d2124af"),
                "07809374470"
        );
    }

}
