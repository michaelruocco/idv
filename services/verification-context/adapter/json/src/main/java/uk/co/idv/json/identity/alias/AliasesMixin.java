package uk.co.idv.json.identity.alias;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.domain.entities.identity.alias.Alias;

import java.util.UUID;

public interface AliasesMixin {

    @JsonIgnore
    UUID getIdvIdValue();

    @JsonIgnore
    Alias getIdvId();

}
