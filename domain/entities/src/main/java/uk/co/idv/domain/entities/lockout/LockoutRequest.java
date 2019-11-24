package uk.co.idv.domain.entities.lockout;

import uk.co.idv.domain.entities.identity.alias.Alias;


public interface LockoutRequest extends LockoutPolicyRequest {

    Alias getAlias();

}
