package uk.co.idv.repository.dynamo.identity.alias;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AliasMappingItemConverter {

    private final AliasConverter aliasConverter;

    public Collection<Item> toItems(final Identity identity) {
        final Aliases aliases = identity.getAliases();
        return aliases.stream()
                .map(alias -> toItem(identity, alias))
                .collect(Collectors.toList());
    }

    private Item toItem(final Identity identity, final Alias alias) {
        return new Item()
                .withPrimaryKey("alias", aliasConverter.toString(alias))
                .with("idvId", identity.getIdvIdValue().toString());
    }

    public Collection<Item> queryOutcomesToItems(final ItemCollection<QueryOutcome> items) {
        return IterableUtils.toList(items);
    }

    public Identity toIdentity(final Collection<Item> items) {
        final Collection<Alias> aliases = items.stream()
                .map(item -> aliasConverter.toAlias(item.getString("alias")))
                .collect(Collectors.toList());
        return new Identity(Aliases.with(aliases));
    }

}
