package uk.co.idv.domain.usecases.verificationcontext;

import uk.co.idv.domain.entities.identity.Identity;

public interface IdentityUpserter {

    Identity upsertIdentity(CreateContextRequest request);

}
