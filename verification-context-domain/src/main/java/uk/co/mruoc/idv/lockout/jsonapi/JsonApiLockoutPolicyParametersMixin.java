package uk.co.mruoc.idv.lockout.jsonapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.mruoc.idv.lockout.api.LockoutPolicyParametersMixin;

import java.util.UUID;

public interface JsonApiLockoutPolicyParametersMixin extends LockoutPolicyParametersMixin {

    @JsonIgnore
    UUID getId();

}
