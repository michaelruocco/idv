package uk.co.mruoc.idv.identity.domain.service;

import uk.co.mruoc.idv.identity.domain.model.Identity;

public interface IdentityService {

    Identity upsert(UpsertIdentityRequest request);

}
