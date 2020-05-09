package uk.co.idv.json.verificationcontext.policy;

import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodPolicy;

import java.util.Collection;

public interface VerificationSequencePolicyMixin {

    @JsonProperty("methods")
    Collection<VerificationMethodPolicy> getMethodPolicies();

}
