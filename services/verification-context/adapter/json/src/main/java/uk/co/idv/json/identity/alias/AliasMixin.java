package uk.co.idv.json.identity.alias;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface AliasMixin {

    @JsonIgnore
    boolean isCardNumber();

}
