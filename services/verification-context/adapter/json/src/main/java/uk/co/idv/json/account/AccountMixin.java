package uk.co.idv.json.account;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({
        "status",
        "cardNumbers"
})
public interface AccountMixin {

    // intentionally blank

}
