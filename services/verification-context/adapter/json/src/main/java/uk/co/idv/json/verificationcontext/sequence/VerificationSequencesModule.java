package uk.co.idv.json.verificationcontext.sequence;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;

public class VerificationSequencesModule extends SimpleModule {

    public VerificationSequencesModule() {
        super("verification-sequences-module", Version.unknownVersion());

        setMixInAnnotation(VerificationSequence.class, VerificationSequenceMixin.class);
        setMixInAnnotation(VerificationSequences.class, VerificationSequencesMixin.class);

        addDeserializer(VerificationSequences.class, new VerificationSequencesDeserializer());
        addDeserializer(VerificationSequence.class, new VerificationSequenceDeserializer());
    }

}
