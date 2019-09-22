package uk.co.mruoc.idv.verificationcontext.jsonapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.mruoc.idv.verificationcontext.api.VerificationContextMixin;

import java.util.UUID;

public interface JsonApiVerificationContextMixin extends VerificationContextMixin {

    @JsonIgnore
    UUID getId();

}
