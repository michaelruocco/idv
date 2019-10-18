package uk.co.mruoc.idv.identity.domain.service;

import lombok.Getter;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Identity;

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
