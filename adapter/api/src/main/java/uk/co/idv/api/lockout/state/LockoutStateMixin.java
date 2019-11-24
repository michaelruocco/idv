package uk.co.idv.api.lockout.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.UUID;

@JsonPropertyOrder({
        "id",
        "idvId",
        "locked",
        "attempts"
})
public interface LockoutStateMixin {

    @JsonIgnore
    UUID getId();

    @JsonIgnore
    String getMessage();

}
