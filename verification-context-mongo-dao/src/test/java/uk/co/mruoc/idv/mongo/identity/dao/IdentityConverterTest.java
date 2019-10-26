package uk.co.mruoc.idv.mongo.identity.dao;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.AliasesMother;
import uk.co.mruoc.idv.identity.domain.model.Identity;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IdentityConverterTest {

    private final AliasesConverter aliasesConverter = mock(AliasesConverter.class);

    private final IdentityConverter converter = new IdentityConverter(aliasesConverter);

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
