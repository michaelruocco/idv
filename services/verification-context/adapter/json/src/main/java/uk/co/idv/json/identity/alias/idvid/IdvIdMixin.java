package uk.co.idv.json.identity.alias.idvid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.json.identity.alias.AliasMixin;

import java.util.UUID;

public interface IdvIdMixin extends AliasMixin {

    @JsonIgnore
    UUID getValueAsUuid();

}
