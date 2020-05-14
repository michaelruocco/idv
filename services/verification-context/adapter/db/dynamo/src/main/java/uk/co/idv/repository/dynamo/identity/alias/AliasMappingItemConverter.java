package uk.co.idv.repository.dynamo.identity.alias;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.utils.json.converter.JsonConverter;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AliasMappingItemConverter {

    private final AliasConverter aliasConverter;
    private final JsonConverter jsonConverter;

    public Collection<Item> toItems(final Identity identity) {
        final Aliases aliases = identity.getAliases();
        return aliases.stream()
                .map(alias -> toItem(identity, alias))
                .collect(Collectors.toList());
    }

    private Item toItem(final Identity identity, final Alias alias) {
        return new Item()
                .withPrimaryKey("alias", aliasConverter.toString(alias))
                .with("idvId", identity.getIdvIdValue().toString())
                .withJSON("body", jsonConverter.toJson(identity));
    }

    public Collection<Item> queryOutcomesToItems(final ItemCollection<QueryOutcome> items) {
        return IterableUtils.toList(items);
    }

    public Identity toIdentity(final Item item) {
        return jsonConverter.toObject(item.getJSON("body"), Identity.class);
    }

}
