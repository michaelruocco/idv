package uk.co.idv.domain.entities.verificationcontext.method;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod.CannotAddResultToMethodException;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod.MethodAlreadyCompleteException;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

public class VerificationMethodUtils {

    private VerificationMethodUtils() {
        // utility class
    }

    public static void addResult(final VerificationResults results,
                                 final VerificationResult result,
                                 final String name,
                                 final int maxAttempts) {
        if (!name.equals(result.getMethodName())) {
            throw new CannotAddResultToMethodException(result.getMethodName(), name);
        }
        if (isComplete(results, maxAttempts)) {
            throw new MethodAlreadyCompleteException(name);
        }
        results.add(result);
    }

    public static boolean isComplete(final VerificationResults results, final int maxAttempts) {
        if (results.containsSuccessful()) {
            return true;
        }
        return results.size() >= maxAttempts;
    }

}
