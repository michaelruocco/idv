package uk.co.idv.api.verification.onetimepasscode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.json.verification.onetimepasscode.OneTimePasscodeVerificationMixin;

import java.util.UUID;

public interface ApiOneTimePasscodeVerificationMixin extends OneTimePasscodeVerificationMixin {

    @JsonIgnore
    UUID getId();

}
