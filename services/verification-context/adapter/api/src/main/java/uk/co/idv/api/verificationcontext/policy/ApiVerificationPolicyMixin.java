package uk.co.idv.api.verificationcontext.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.json.verificationcontext.policy.VerificationPolicyMixin;

import java.util.UUID;

public interface ApiVerificationPolicyMixin extends VerificationPolicyMixin {

    @JsonIgnore
    UUID getId();

}
