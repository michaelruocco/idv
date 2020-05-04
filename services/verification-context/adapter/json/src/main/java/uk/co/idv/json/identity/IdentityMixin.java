package uk.co.idv.json.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;

import java.util.UUID;

@JsonPropertyOrder({
        "id",
        "aliases",
        "accounts",
        "phoneNumbers",
        "mobileDevices"
})
public interface IdentityMixin {

    @JsonProperty("id")
    UUID getIdvIdValue();

    @JsonIgnore
    PhoneNumbers getMobilePhoneNumbers();

}
