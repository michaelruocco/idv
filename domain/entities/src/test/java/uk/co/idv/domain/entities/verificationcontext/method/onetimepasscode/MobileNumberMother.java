package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;



import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class MobileNumberMother {

    private MobileNumberMother() {
        // utility class
    }

    public static Collection<MobileNumber> onePrimary() {
        return Collections.singleton(primary());
    }

    public static MobileNumber primary() {
        return new MobileNumber(
                UUID.fromString("2a82fcb5-19d4-469d-9c1b-4b2318c1e3f4"),
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
