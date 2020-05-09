package uk.co.idv.json.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface PolicyLevelMixin {

    @JsonIgnore
    boolean isAliasLevel();

}
