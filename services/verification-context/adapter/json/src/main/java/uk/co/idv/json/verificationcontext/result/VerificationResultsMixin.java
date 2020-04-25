package uk.co.idv.json.verificationcontext.result;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;

public interface VerificationResultsMixin {

    @JsonIgnore
    boolean isEmpty();

    @JsonIgnore
    Collection<String> getMethodNames();

}
