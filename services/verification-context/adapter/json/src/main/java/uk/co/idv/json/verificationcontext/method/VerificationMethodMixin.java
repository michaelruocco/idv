package uk.co.idv.json.verificationcontext.method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;


@JsonPropertyOrder({
        "name",
        "eligible",
        "reason",
        "complete",
        "successful"
})
public interface VerificationMethodMixin {

    @JsonIgnore
    Eligibility getEligibility();

    @JsonProperty("reason")
    String getEligibilityReason();

}
