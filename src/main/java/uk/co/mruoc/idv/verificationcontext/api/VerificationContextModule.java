package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PasscodeSettings;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

public class VerificationContextModule extends SimpleModule {

    public VerificationContextModule() {
        setMixInAnnotation(VerificationContext.class, VerificationContextMixin.class);
        addSerializer(VerificationSequences.class, new VerificationSequencesSerializer());
        addSerializer(VerificationSequence.class, new VerificationSequenceSerializer());
        addSerializer(VerificationMethod.class, new VerificationMethodSerializer());
        addSerializer(PasscodeSettings.class, new PasscodeSettingsSerializer());
    }

}
