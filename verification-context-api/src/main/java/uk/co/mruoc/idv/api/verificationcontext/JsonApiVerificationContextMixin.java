package uk.co.mruoc.idv.api.verificationcontext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextMixin;

import java.util.UUID;

public interface JsonApiVerificationContextMixin extends VerificationContextMixin {

    @JsonIgnore
    UUID getId();

}
