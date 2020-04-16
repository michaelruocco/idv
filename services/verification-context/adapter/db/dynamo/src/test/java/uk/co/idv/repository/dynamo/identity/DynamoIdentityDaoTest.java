package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.repository.dynamo.identity.alias.AliasConverter;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingItemConverter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class DynamoIdentityDaoTest {

    private final Table table = mock(Table.class);
    private final Index idvIdIndex = mock(Index.class);

    private final AliasMappingItemConverter itemConverter = mock(AliasMappingItemConverter.class);
    private final AliasConverter aliasConverter = mock(AliasConverter.class);

    private IdentityDao dao;

    @BeforeEach
    void setUp() {
        given(table.getIndex("idvIdIndex")).willReturn(idvIdIndex);
        dao = DynamoIdentityDao.builder()
                .table(table)
                .itemConverter(itemConverter)
                .aliasConverter(aliasConverter)
                .build();
    }

    @Test
    void shouldReturnEmptyOptionalIfIdentityWithIdvIdNotFound() {
        final Alias idvIdAlias = AliasesMother.randomIdvId();
        final ItemCollection<QueryOutcome> queryOutcomes = mock(ItemCollection.class);
        given(idvIdIndex.query(any(QuerySpec.class))).willReturn(queryOutcomes);
        given(itemConverter.queryOutcomesToItems(queryOutcomes)).willReturn(Collections.emptyList());

        final Optional<Identity> identity = dao.load(idvIdAlias);

        assertThat(identity).isEmpty();
    }

    @Test
    void shouldReturnIdentityIfIdentityWithIdvIdFound() {
        final Alias idvIdAlias = AliasesMother.randomIdvId();
        final ItemCollection<QueryOutcome> queryOutcomes = mock(ItemCollection.class);
        given(idvIdIndex.query(any(QuerySpec.class))).willReturn(queryOutcomes);
        final Item item = new Item();
        final Collection<Item> items = Collections.singleton(item);
        given(itemConverter.queryOutcomesToItems(queryOutcomes)).willReturn(items);
        final Identity expectedIdentity = IdentityMother.withAliases(AliasesMother.aliases());
        given(itemConverter.toIdentity(items)).willReturn(expectedIdentity);

        final Optional<Identity> identity = dao.load(idvIdAlias);

        assertThat(identity).contains(expectedIdentity);
    }

    @Test
    void shouldReturnEmptyOptionalIfIdentityWithOtherAliasNotFound() {
        final Alias alias = AliasesMother.creditCardNumber();
        final ItemCollection<QueryOutcome> queryOutcomes = mock(ItemCollection.class);
        given(idvIdIndex.query(any(QuerySpec.class))).willReturn(queryOutcomes);
        given(itemConverter.queryOutcomesToItems(queryOutcomes)).willReturn(Collections.emptyList());

        final Optional<Identity> identity = dao.load(alias);

        assertThat(identity).isEmpty();
    }

    @Test
    void shouldReturnIdentityIfIdentityWithOtherAliasFound() {
        final Alias alias = AliasesMother.creditCardNumber();
        final String key = "alias-string";
        given(aliasConverter.toString(alias)).willReturn(key);
        final Item item = new Item().withString("idvId", UUID.randomUUID().toString());
        given(table.getItem("alias", key)).willReturn(item);
        final ItemCollection<QueryOutcome> queryOutcomes = mock(ItemCollection.class);
        given(idvIdIndex.query(any(QuerySpec.class))).willReturn(queryOutcomes);
        final Collection<Item> items = Collections.singleton(item);
        given(itemConverter.queryOutcomesToItems(queryOutcomes)).willReturn(items);
        final Identity expectedIdentity = IdentityMother.withAliases(AliasesMother.aliases());
        given(itemConverter.toIdentity(items)).willReturn(expectedIdentity);

        final Optional<Identity> identity = dao.load(alias);

        assertThat(identity).contains(expectedIdentity);
    }

    @Test
    void shouldSaveAllAliasesIfIdentityDoesNotExist() {
        final Identity identity = IdentityMother.withAliases(AliasesMother.aliases());
        final Item item1 = new Item().with("alias", "item1");
        final Item item2 = new Item().with("alias", "item2");
        final Collection<Item> items = Arrays.asList(item1, item2);
        given(itemConverter.toItems(identity)).willReturn(items);

        dao.save(identity);

        verify(table, never()).deleteItem(eq("alias"), anyString());
        verify(table).putItem(item1);
        verify(table).putItem(item2);
    }


    @Test
    void shouldDeleteAliasesIfAliasesExistButNotPresentOnUpdatedIdentity() {
        final Identity identity = IdentityMother.withAliases(AliasesMother.aliases());
        final Item item1 = new Item().with("alias", "item1");
        final Item item2 = new Item().with("alias", "item2");
        final Collection<Item> newItems = Arrays.asList(item1, item2);
        given(itemConverter.toItems(identity)).willReturn(newItems);

        final ItemCollection<QueryOutcome> queryOutcomes = mock(ItemCollection.class);
        given(idvIdIndex.query(any(QuerySpec.class))).willReturn(queryOutcomes);
        final Item item3 = new Item().with("alias", "item3");
        final Collection<Item> existingItems = Arrays.asList(item1, item3);
        given(itemConverter.queryOutcomesToItems(queryOutcomes)).willReturn(existingItems);
        dao.save(identity);

        verify(table).deleteItem("alias", item3.getString("alias"));
        verify(table).putItem(item1);
        verify(table).putItem(item2);
    }

}
