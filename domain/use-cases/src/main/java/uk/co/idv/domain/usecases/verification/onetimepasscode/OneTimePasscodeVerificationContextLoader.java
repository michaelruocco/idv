package uk.co.idv.domain.usecases.verification.onetimepasscode;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences.NotNextMethodInSequenceException;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader;

import java.util.UUID;

@RequiredArgsConstructor
public class OneTimePasscodeVerificationContextLoader {

    private final String methodName;
    private final VerificationContextLoader contextLoader;

    public OneTimePasscodeVerificationContextLoader(final VerificationContextLoader contextLoader) {
        this(OneTimePasscode.NAME, contextLoader);
    }

    public VerificationContext load(final UUID id) {
        final VerificationContext context = contextLoader.load(id);
        if (context.containsSequenceWithNextMethod(methodName)) {
            return context;
        }
        throw new NotNextMethodInSequenceException(methodName);
    }

}
