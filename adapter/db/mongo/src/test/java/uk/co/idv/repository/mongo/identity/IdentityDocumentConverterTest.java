package uk.co.idv.repository.mongo.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.repository.mongo.identity.alias.AliasDocument;
import uk.co.idv.repository.mongo.identity.alias.AliasesDocumentConverter;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.identity.Identity;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IdentityDocumentConverterTest {

    private final AliasesDocumentConverter aliasesConverter = mock(AliasesDocumentConverter.class);

    private final IdentityDocumentConverter converter = new IdentityDocumentConverter(aliasesConverter);

    @Test
    void shouldConvertAliasesToIdentity() {
        final IdentityDocument identityDocument = IdentityDocumentMother.build();
        final Aliases aliases = Aliases.empty();
        given(aliasesConverter.toAliases(identityDocument.getAliases())).willReturn(aliases);

        final Identity identity = converter.toIdentity(identityDocument);

        assertThat(identity.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldConvertIdvIdValueToDocument() {
        final Identity identity = new Identity(AliasesMother.aliases());

        final IdentityDocument document = converter.toDocument(identity);

        assertThat(document.getId()).isEqualTo(identity.getIdvIdValue().toString());
    }

    @Test
    void shouldConvertAliasesToDocument() {
        final Identity identity = new Identity(AliasesMother.aliases());
        final Collection<AliasDocument> aliasDocuments = Collections.emptyList();
        given(aliasesConverter.toDocuments(identity.getAliases())).willReturn(aliasDocuments);

        final IdentityDocument document = converter.toDocument(identity);

        assertThat(document.getAliases()).isEqualTo(aliasDocuments);
    }
}
