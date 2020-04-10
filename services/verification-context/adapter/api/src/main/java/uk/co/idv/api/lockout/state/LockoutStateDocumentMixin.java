package uk.co.idv.api.lockout.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;

public interface LockoutStateDocumentMixin {

    @JsonIgnore
    LockoutState getAttributes();

}
