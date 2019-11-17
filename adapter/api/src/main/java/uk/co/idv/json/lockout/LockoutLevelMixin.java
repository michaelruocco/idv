package uk.co.idv.json.lockout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "channelId",
        "activityName",
        "aliasType"
})
public interface LockoutLevelMixin {

    @JsonIgnore
    boolean isAliasLevel();

}
