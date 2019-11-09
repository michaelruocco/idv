package uk.co.idv.repository.inmemory.identity;

import uk.co.mruoc.idv.identity.dao.IdentityDao;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.Identity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryIdentityDao implements IdentityDao {

    private final Map<String, Identity> identities = new HashMap<>();

    @Override
    public void save(final Identity identity) {
        // TODO need to ensure all entries are removed correctly when an identity is
        // deleted in future, or if an identity is saved with an alias removed
        final Collection<String> keys = toKeys(identity);
        keys.forEach(key -> identities.put(key, identity));
    }

    @Override
    public Optional<Identity> load(final Alias alias) {
        final String key = toKey(alias);
        return Optional.ofNullable(identities.get(key));
    }

    private static Collection<String> toKeys(final Identity identity) {
        final Aliases aliases = identity.getAliases();
        return aliases.stream()
                .map(InMemoryIdentityDao::toKey)
                .collect(Collectors.toList());
    }

    private static String toKey(final Alias alias) {
        return String.format("%s-%s", alias.getType(), alias.getValue());
    }

}
