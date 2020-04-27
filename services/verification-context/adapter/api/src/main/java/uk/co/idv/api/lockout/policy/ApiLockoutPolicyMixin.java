package uk.co.idv.api.lockout.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.json.lockout.policy.LockoutPolicyMixin;

import java.util.UUID;

public interface ApiLockoutPolicyMixin extends LockoutPolicyMixin {

    @JsonIgnore
    UUID getId();

}
