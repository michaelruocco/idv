package uk.co.idv.json.identity.alias;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public interface AliasesMixin {

    @JsonIgnore
    UUID getIdvIdValue();

}
