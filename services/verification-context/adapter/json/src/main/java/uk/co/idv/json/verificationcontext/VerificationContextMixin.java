package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;

import java.util.UUID;

@JsonPropertyOrder({
        "id",
        "channel",
        "providedAlias",
        "identity",
        "activity",
        "sequences",
        "created",
        "expiry"
})
public interface VerificationContextMixin {

    @JsonIgnore
    String getChannelId();

    @JsonIgnore
    String getActivityName();

    @JsonIgnore
    UUID getIdvIdValue();

    @JsonIgnore
    OneTimePasscode getNextOneTimePasscodeEligibleMethod();

}
