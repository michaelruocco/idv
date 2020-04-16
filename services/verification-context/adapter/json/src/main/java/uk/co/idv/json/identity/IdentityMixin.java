package uk.co.idv.json.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;

import java.util.Collection;
import java.util.UUID;

@JsonPropertyOrder({
        "id",
        "aliases"
})
public interface IdentityMixin {

    @JsonProperty("id")
    UUID getIdvIdValue();

    @JsonIgnore
    PhoneNumbers getMobilePhoneNumbers();

    @JsonIgnore
    PhoneNumbers getPhoneNumbers();

    @JsonIgnore
    Collection<Account> getAccounts();

}
