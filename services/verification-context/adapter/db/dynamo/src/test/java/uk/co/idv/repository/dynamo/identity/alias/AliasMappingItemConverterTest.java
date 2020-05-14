package uk.co.idv.repository.dynamo.identity.alias;

import com.amazonaws.services.dynamodbv2.document.Item;
import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.utils.json.converter.JsonConverter;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AliasMappingItemConverterTest {

    private final AliasConverter aliasConverter = mock(AliasConverter.class);
    private final JsonConverter jsonConverter = mock(JsonConverter.class);

    private final AliasMappingItemConverter converter = new AliasMappingItemConverter(aliasConverter, jsonConverter);

    @Test
    void shouldConvertIdentityToMappingDocumentForEachAlias() {
        final Aliases aliases = AliasesMother.aliases();
        final Identity identity = IdentityMother.withAliases(aliases);
        given(jsonConverter.toJson(identity)).willReturn("{}");

        final Collection<Item> items = converter.toItems(identity);

        assertThat(items).hasSize(aliases.size());
    }

    @Test
    void shouldConvertAliasToMappingDocument() {
        final Alias alias = AliasesMother.idvId();
        final Identity identity = IdentityMother.withAliases(Aliases.with(alias));
        final String expectedAliasString = "alias-string";
        given(aliasConverter.toString(alias)).willReturn(expectedAliasString);
        given(jsonConverter.toJson(identity)).willReturn("{}");

        final Collection<Item> items = converter.toItems(identity);

        final Item item = IterableUtils.get(items, 0);
        assertThat(item.getString("alias")).isEqualTo(expectedAliasString);
    }

    @Test
    void shouldCovertIdvIdToAllMappingDocuments() {
        final Alias idvId = AliasesMother.idvId();
        final Alias creditCardNumber = AliasesMother.creditCardNumber();
        final Identity identity = IdentityMother.withAliases(Aliases.with(idvId, creditCardNumber));
        given(jsonConverter.toJson(identity)).willReturn("{}");

        final Collection<Item> items = converter.toItems(identity);

        items.forEach(item -> assertThat(item.getString("idvId")).isEqualTo(idvId.getValue()));
    }

    @Test
    void shouldConvertMappingDocumentsToIdentity() {
        final Item item = toItem("alias1");
        final Alias alias = AliasesMother.idvId();
        given(aliasConverter.toAlias(item.getString("alias"))).willReturn(alias);

        final Identity expectedIdentity = IdentityMother.withAliases(Aliases.with(alias));
        given(jsonConverter.toObject(item.getJSON("body"), Identity.class)).willReturn(expectedIdentity);

        final Identity identity = converter.toIdentity(item);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

    private Item toItem(final String alias) {
        return new Item()
                .with("alias", alias)
                .withJSON("body", "{}");
    }

}
