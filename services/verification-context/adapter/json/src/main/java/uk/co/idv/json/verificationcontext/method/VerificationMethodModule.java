package uk.co.idv.json.verificationcontext.method;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.json.verificationcontext.result.VerificationResultsModule;

import java.time.Duration;
import java.util.Arrays;

public class VerificationMethodModule extends SimpleModule {

    public VerificationMethodModule() {
        super("default-verification-method-module", Version.unknownVersion());

        setMixInAnnotation(VerificationMethod.class, VerificationMethodMixin.class);

        addSerializer(Duration.class, new DurationSerializer());

        addDeserializer(VerificationMethod.class, new VerificationMethodDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new Jdk8Module(),
                new JavaTimeModule(),
                new VerificationResultsModule()
        );
    }

}
