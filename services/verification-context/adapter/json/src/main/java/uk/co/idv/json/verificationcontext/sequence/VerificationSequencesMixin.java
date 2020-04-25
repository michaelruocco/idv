package uk.co.idv.json.verificationcontext.sequence;

import com.fasterxml.jackson.annotation.JsonIgnore;


public interface VerificationSequencesMixin {

    @JsonIgnore
    boolean isEmpty();

}
