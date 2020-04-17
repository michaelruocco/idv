package uk.co.idv.json.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;

import java.util.Collection;
import java.util.UUID;

@JsonPropertyOrder({
        "id",
        "aliases",
        "accounts",
        "phoneNumbers"
})
public interface IdentityMixin {

    @JsonProperty("id")
    UUID getIdvIdValue();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Collection<Account> getAccounts();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    PhoneNumbers getPhoneNumbers();

    @JsonIgnore
    PhoneNumbers getMobilePhoneNumbers();

}
