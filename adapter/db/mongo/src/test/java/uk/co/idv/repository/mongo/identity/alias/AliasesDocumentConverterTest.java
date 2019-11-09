package uk.co.idv.repository.mongo.identity.alias;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.Alias;
import uk.co.idv.domain.entities.identity.Aliases;
import uk.co.idv.domain.entities.identity.AliasesMother;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AliasesDocumentConverterTest {

    private final AliasDocument document1 = AliasDocumentMother.idvId();
    private final AliasDocument document2 = AliasDocumentMother.creditCard();

    private final Alias alias1 = AliasesMother.idvId();
    private final Alias alias2 = AliasesMother.creditCardNumber();

    private final AliasDocumentConverter aliasConverter = mock(AliasDocumentConverter.class);

    private final AliasesDocumentConverter aliasesConverter = new AliasesDocumentConverter(aliasConverter);

    @Test
    void shouldConvertMultipleDocumentsToAliases() {
        given(aliasConverter.toAlias(document1)).willReturn(alias1);
        given(aliasConverter.toAlias(document2)).willReturn(alias2);

        final Aliases aliases = aliasesConverter.toAliases(Arrays.asList(document1, document2));

        assertThat(aliases).containsExactly(alias1, alias2);
    }

    @Test
    void shouldConvertMultipleAliasesToDocuments() {
        given(aliasConverter.toDocument(alias1)).willReturn(document1);
        given(aliasConverter.toDocument(alias2)).willReturn(document2);

        final Collection<AliasDocument> documents = aliasesConverter.toDocuments(Aliases.with(alias1, alias2));

        assertThat(documents).containsExactly(document1, document2);
    }

}
