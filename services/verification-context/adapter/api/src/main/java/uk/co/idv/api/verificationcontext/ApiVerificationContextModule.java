
package uk.co.idv.api.verificationcontext;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.api.lockout.ApiLockoutStateModule;
import uk.co.idv.api.verificationcontext.policy.ApiVerificationPolicyModule;
import uk.co.idv.json.lockout.policy.LockoutPolicyModule;
import uk.co.idv.json.verificationcontext.VerificationContextModule;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.mruoc.jsonapi.ApiModule;

import java.util.Arrays;

public class ApiVerificationContextModule extends SimpleModule {

    public ApiVerificationContextModule() {
        super("api-verification-context-module", Version.unknownVersion());

        setMixInAnnotation(VerificationContext.class, ApiVerificationContextMixin.class);

        addDeserializer(CreateContextRequestDocument.class, new CreateContextRequestDocumentDeserializer());
        addDeserializer(UpdateContextResultsRequestDocument.class, new UpdateContextResultsRequestDocumentDeserializer());
        addDeserializer(VerificationContextDocument.class, new VerificationContextDocumentDeserializer());
    }

    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new VerificationContextModule(),
                new LockoutPolicyModule(),
                new ApiModule(),
                new ApiVerificationPolicyModule(),
                new ApiLockoutStateModule()
        );
    }

}
