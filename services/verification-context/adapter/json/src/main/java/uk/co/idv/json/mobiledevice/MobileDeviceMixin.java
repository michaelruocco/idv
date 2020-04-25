package uk.co.idv.json.mobiledevice;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({
        "id",
        "lastLogin",
        "trusted"
})
public interface MobileDeviceMixin {

    // intentionally blank

}
