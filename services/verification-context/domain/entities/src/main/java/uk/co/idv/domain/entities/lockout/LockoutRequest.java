package uk.co.idv.domain.entities.lockout;

import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.policy.PolicyRequest;


public interface LockoutRequest extends PolicyRequest {

    Alias getAlias();

}
