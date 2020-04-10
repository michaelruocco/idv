package uk.co.idv.api.verificationcontext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.json.verificationcontext.VerificationContextMixin;

import java.util.UUID;

public interface ApiVerificationContextMixin extends VerificationContextMixin {

    @JsonIgnore
    UUID getId();

}
