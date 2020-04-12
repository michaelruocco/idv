package uk.co.idv.json.lockout.attempt;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.json.identity.AliasModule;

import java.util.Arrays;

public class VerificationAttemptsModule extends SimpleModule {

    public VerificationAttemptsModule() {
        super("verification-attempts-module", Version.unknownVersion());

        setMixInAnnotation(VerificationAttempt.class, VerificationAttemptMixin.class);

        addSerializer(VerificationAttempts.class, new VerificationAttemptsSerializer());

        addDeserializer(VerificationAttempts.class, new VerificationAttemptsDeserializer());
        addDeserializer(VerificationAttempt.class, new VerificationAttemptDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new JavaTimeModule(),
                new AliasModule()
        );
    }

}
