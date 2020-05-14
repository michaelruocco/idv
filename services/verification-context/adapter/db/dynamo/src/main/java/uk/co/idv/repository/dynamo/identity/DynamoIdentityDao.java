package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.IdvId;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.repository.dynamo.identity.alias.AliasConverter;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingItemConverter;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class DynamoIdentityDao implements IdentityDao {

    private final Table table;
    private final Index idvIdIndex;

    private final AliasMappingItemConverter itemConverter;
    private final AliasConverter aliasConverter;

    @Builder
    public DynamoIdentityDao(final Table table,
                             final AliasMappingItemConverter itemConverter,
                             final AliasConverter aliasConverter) {
        this.table = table;
        this.idvIdIndex = table.getIndex("idvIdIndex");
        this.itemConverter = itemConverter;
        this.aliasConverter = aliasConverter;
    }

    @Override
    public void save(final Identity identity) {
        log.debug("saving identity {}", identity);
        final Collection<Item> updateItems = itemConverter.toItems(identity);
        final Collection<Item> existingItems = loadItemsByIdvId(identity.getIdvIdValue());
        log.debug("updateItems {}", updateItems);
        log.debug("exitingItems {}", existingItems);
        final Collection<Item> itemsToDelete = CollectionUtils.subtract(existingItems, updateItems);
        log.debug("itemsToDelete {}", itemsToDelete);

        deleteAll(itemsToDelete);
        saveAll(updateItems);
    }

    @Override
    public Optional<Identity> load(final Alias alias) {
        if (IdvId.isIdvId(alias)) {
            return loadByIdvId(alias);
        }
        return loadByAlias(alias);
    }

    private void deleteAll(final Collection<Item> items) {
        log.debug("deleting items {}", items);
        items.forEach(item -> table.deleteItem("alias", item.getString("alias")));
    }

    private void saveAll(final Collection<Item> items) {
        log.debug("saving items {}", items);
        items.forEach(table::putItem);
    }

    private Optional<Identity> loadByAlias(final Alias alias) {
        final String key = aliasConverter.toString(alias);
        log.info("attempting to load mapping document using key {}", key);
        final Optional<Item> item = Optional.ofNullable(table.getItem("alias", key));
        return item.flatMap(this::loadByIdvId);
    }

    private Optional<Identity> loadByIdvId(final Item item) {
        return loadByIdvId(new IdvId(item.getString("idvId")));
    }

    private Optional<Identity> loadByIdvId(final Alias alias) {
        return loadByIdvId(alias.getValue());
    }

    private Optional<Identity> loadByIdvId(final String idvId) {
        final Collection<Item> items = loadItemsByIdvId(idvId);
        if (items.isEmpty()) {
            log.info("no existing items found for idv id {}", idvId);
            return Optional.empty();
        }
        return Optional.of(itemConverter.toIdentity(IterableUtils.get(items, 0)));
    }

    private Collection<Item> loadItemsByIdvId(final UUID idvId) {
        return loadItemsByIdvId(idvId.toString());
    }

    private Collection<Item> loadItemsByIdvId(final String idvId) {
        log.info("attempting to load items for idv id {}", idvId);
        final QuerySpec query = new IdentityByIdvIdQuery(idvId);
        return itemConverter.queryOutcomesToItems(idvIdIndex.query(query));
    }

}
