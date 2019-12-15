package uk.co.idv.repository.dynamo.identity;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.repository.dynamo.identity.alias.AliasConverter;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingDocument;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingDocumentConverter;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AliasMappingDocumentConverterTest {

    private final AliasConverter aliasConverter = mock(AliasConverter.class);

    private final AliasMappingDocumentConverter converter = new AliasMappingDocumentConverter(aliasConverter);

    @Test
    void shouldConvertIdentityToMappingDocumentForEachAlias() {
        final Aliases aliases = AliasesMother.aliases();
        final Identity identity = new Identity(aliases);

        final Collection<AliasMappingDocument> documents = converter.toDocuments(identity);

        assertThat(documents).hasSize(aliases.size());
    }

    @Test
    void shouldConvertAliasToMappingDocument() {
        final Alias alias = AliasesMother.idvId();
        final Identity identity = new Identity(Aliases.with(alias));
        final String expectedAliasString = "alias-string";
        given(aliasConverter.toString(alias)).willReturn(expectedAliasString);

        final Collection<AliasMappingDocument> documents = converter.toDocuments(identity);

        final AliasMappingDocument document = IterableUtils.get(documents, 0);
        assertThat(document.getAlias()).isEqualTo(expectedAliasString);
    }

    @Test
    void shouldCovertIdvIdToAllMappingDocuments() {
        final Alias idvId = AliasesMother.idvId();
        final Alias creditCardNumber = AliasesMother.creditCardNumber();
        final Identity identity = new Identity(Aliases.with(idvId, creditCardNumber));

        final Collection<AliasMappingDocument> documents = converter.toDocuments(identity);

        documents.forEach(document -> assertThat(document.getIdvId()).isEqualTo(idvId.getValue()));
    }

    @Test
    void shouldConvertMappingDocumentsToIdentity() {
        final AliasMappingDocument document1 = toMappingDocument("alias1");
        final Alias alias1 = AliasesMother.idvId();
        given(aliasConverter.toAlias(document1.getAlias())).willReturn(alias1);

        final AliasMappingDocument document2 = toMappingDocument("alias2");
        final Alias alias2 = AliasesMother.creditCardNumber();
        given(aliasConverter.toAlias(document2.getAlias())).willReturn(alias2);

        final Identity identity = converter.toIdentity(Arrays.asList(document1, document2));

        assertThat(identity.getAliases()).hasSize(2);
        assertThat(identity.hasAlias(alias1)).isTrue();
        assertThat(identity.hasAlias(alias2)).isTrue();
    }

    private AliasMappingDocument toMappingDocument(final String alias) {
        final AliasMappingDocument document = new AliasMappingDocument();
        document.setAlias(alias);
        return document;
    }

}
