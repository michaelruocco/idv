package uk.co.idv.json.verificationcontext.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationSequencePolicy;

import java.util.Collection;

public interface VerificationPolicyMixin {

    @JsonProperty("sequences")
    Collection<VerificationSequencePolicy> getSequencePolicies();

    @JsonIgnore
    boolean isAliasLevel();

}
