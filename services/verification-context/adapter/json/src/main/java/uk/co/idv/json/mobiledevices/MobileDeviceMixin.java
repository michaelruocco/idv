package uk.co.idv.json.mobiledevices;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({
        "id",
        "lastLogin",
        "trusted"
})
public interface MobileDeviceMixin {

    // intentionally blank

}
