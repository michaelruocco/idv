package uk.co.idv.json.lockout.attempt;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

public class VerificationAttemptsModule extends SimpleModule {

    public VerificationAttemptsModule() {
        setMixInAnnotation(VerificationAttempt.class, VerificationAttemptMixin.class);

        addSerializer(VerificationAttempts.class, new VerificationAttemptsSerializer());

        addDeserializer(VerificationAttempts.class, new VerificationAttemptsDeserializer());
        addDeserializer(VerificationAttempt.class, new VerificationAttemptDeserializer());
    }

}
