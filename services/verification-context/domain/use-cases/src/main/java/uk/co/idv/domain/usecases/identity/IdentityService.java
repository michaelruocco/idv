package uk.co.idv.domain.usecases.identity;

import lombok.Getter;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.Identity;

public interface IdentityService {

    Identity upsert(UpsertIdentityRequest request);

    Identity load(LoadIdentityRequest alias);

    @Getter
    class IdentityNotFoundException extends RuntimeException {

        private final Alias alias;

        public IdentityNotFoundException(final Alias alias) {
            super(String.format("aliasType: %s aliasValue: %s", alias.getType(), alias.getValue()));
            this.alias = alias;
        }

    }

}
