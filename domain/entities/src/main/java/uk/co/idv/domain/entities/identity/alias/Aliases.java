package uk.co.idv.domain.entities.identity.alias;

import uk.co.idv.domain.entities.identity.alias.Alias.AliasWithTypeNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableSet;

public class Aliases implements Iterable<Alias> {

    private Collection<Alias> aliases;

    protected Aliases(final Collection<Alias> aliases) {
        this.aliases = unmodifiableSet(new LinkedHashSet<>(aliases));
    }

    @Override
    public Iterator<Alias> iterator() {
        return aliases.iterator();
    }

    public UUID getIdvIdValue() {
        final IdvId idvId = getIdvId();
        return idvId.getValueAsUuid();
    }

    public boolean contains(final Alias alias) {
        return aliases.contains(alias);
    }

    public Stream<Alias> stream() {
        return aliases.stream();
    }

    public int size() {
        return aliases.size();
    }

    private IdvId getIdvId() {
        return (IdvId) getAliasByType(IdvId.TYPE);
    }

    private Alias getAliasByType(final String type) {
        return aliases.stream()
                .filter(alias -> alias.isType(type))
                .findFirst()
                .orElseThrow(() -> new AliasWithTypeNotFoundException(type));
    }

    public static Aliases empty() {
        return with();
    }

    public static Aliases with(final Alias... aliases) {
        return with(Arrays.asList(aliases));
    }

    public static Aliases with(final Collection<Alias> aliases) {
        return new Aliases(aliases);
    }

}
