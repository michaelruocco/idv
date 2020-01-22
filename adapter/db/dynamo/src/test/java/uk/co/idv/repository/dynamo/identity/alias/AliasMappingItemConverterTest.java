package uk.co.idv.repository.dynamo.identity.alias;

import com.amazonaws.services.dynamodbv2.document.Item;
import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AliasMappingItemConverterTest {

    private final AliasConverter aliasConverter = mock(AliasConverter.class);

    private final AliasMappingItemConverter converter = new AliasMappingItemConverter(aliasConverter);

    @Test
    void shouldConvertIdentityToMappingDocumentForEachAlias() {
        final Aliases aliases = AliasesMother.aliases();
        final Identity identity = new Identity(aliases);

        final Collection<Item> items = converter.toItems(identity);

        assertThat(items).hasSize(aliases.size());
    }

    @Test
    void shouldConvertAliasToMappingDocument() {
        final Alias alias = AliasesMother.idvId();
        final Identity identity = new Identity(Aliases.with(alias));
        final String expectedAliasString = "alias-string";
        given(aliasConverter.toString(alias)).willReturn(expectedAliasString);

        final Collection<Item> items = converter.toItems(identity);

        final Item item = IterableUtils.get(items, 0);
        assertThat(item.getString("alias")).isEqualTo(expectedAliasString);
    }

    @Test
    void shouldCovertIdvIdToAllMappingDocuments() {
        final Alias idvId = AliasesMother.idvId();
        final Alias creditCardNumber = AliasesMother.creditCardNumber();
        final Identity identity = new Identity(Aliases.with(idvId, creditCardNumber));

        final Collection<Item> items = converter.toItems(identity);

        items.forEach(item -> assertThat(item.getString("idvId")).isEqualTo(idvId.getValue()));
    }

    @Test
    void shouldConvertMappingDocumentsToIdentity() {
        final Item item1 = toItem("alias1");
        final Alias alias1 = AliasesMother.idvId();
        given(aliasConverter.toAlias(item1.getString("alias"))).willReturn(alias1);

        final Item item2 = toItem("alias2");
        final Alias alias2 = AliasesMother.creditCardNumber();
        given(aliasConverter.toAlias(item2.getString("alias"))).willReturn(alias2);

        final Identity identity = converter.toIdentity(Arrays.asList(item1, item2));

        assertThat(identity.getAliases()).hasSize(2);
        assertThat(identity.hasAlias(alias1)).isTrue();
        assertThat(identity.hasAlias(alias2)).isTrue();
    }

    private Item toItem(final String alias) {
        return new Item()
                .with("alias", alias);
    }

}
