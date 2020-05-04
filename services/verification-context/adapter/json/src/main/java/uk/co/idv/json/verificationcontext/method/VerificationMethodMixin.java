package uk.co.idv.json.verificationcontext.method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;

import java.time.Duration;


@JsonPropertyOrder({
        "name",
        "eligible",
        "reason",
        "complete",
        "successful",
        "params"
})
public interface VerificationMethodMixin {

    @JsonProperty("reason")
    String getEligibilityReason();

    @JsonProperty("parameters")
    VerificationMethodParams getParams();

    @JsonIgnore
    Eligibility getEligibility();

    @JsonIgnore
    int getMaxAttempts();

    @JsonIgnore
    Duration getDuration();

}
