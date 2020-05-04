package uk.co.idv.json.onetimepasscode;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({
        "method",
        "passcode",
        "message",
        "sent",
        "expiry"
})
public interface OneTimePasscodeDeliveryMixin {

    // intentionally blank

}
