package uk.co.mruoc.idv.identity.dao;

import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Identity;

import java.util.Optional;

public interface IdentityDao {

    void save(final Identity identity);

    Optional<Identity> load(final Alias alias);

}
