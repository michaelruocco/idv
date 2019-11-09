package uk.co.idv.repository.mongo.identity.alias;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.Alias;
import uk.co.idv.domain.entities.identity.AliasFactory;
import uk.co.idv.domain.entities.identity.AliasesMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AliasDocumentConverterTest {

    private final AliasFactory aliasFactory = mock(AliasFactory.class);
    private final AliasDocumentConverter converter = new AliasDocumentConverter(aliasFactory);

    @Test
    void shouldConvertDocumentToAlias() {
        final AliasDocument document = AliasDocumentMother.idvId();
        final Alias expectedAlias = mock(Alias.class);
        given(aliasFactory.build(document.getType(), document.getValue())).willReturn(expectedAlias);

        final Alias alias = converter.toAlias(document);

        assertThat(alias).isEqualTo(expectedAlias);
    }

    @Test
    void shouldPopulateTypeOnDocument() {
        final Alias alias = AliasesMother.creditCardNumber();

        final AliasDocument document = converter.toDocument(alias);

        assertThat(document.getType()).isEqualTo(alias.getType());
    }

    @Test
    void shouldPopulateValueOnDocument() {
        final Alias alias = AliasesMother.creditCardNumber();

        final AliasDocument document = converter.toDocument(alias);

        assertThat(document.getValue()).isEqualTo(alias.getValue());
    }

}
