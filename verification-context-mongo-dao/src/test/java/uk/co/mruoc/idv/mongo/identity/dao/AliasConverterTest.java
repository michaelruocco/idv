package uk.co.mruoc.idv.mongo.identity.dao;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.AliasFactory;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AliasConverterTest {

    private final AliasFactory aliasFactory = mock(AliasFactory.class);
    private final AliasConverter converter = new AliasConverter(aliasFactory);

    @Test
    void shouldConvertDocumentToAlias() {
        final AliasDocument document = new FakeCreditCardNumberAliasDocument();
        final Alias expectedAlias = mock(Alias.class);
        given(aliasFactory.build(document.getType(), document.getValue())).willReturn(expectedAlias);

        final Alias alias = converter.toAlias(document);

        assertThat(alias).isEqualTo(expectedAlias);
    }

    @Test
    void shouldPopulateTypeOnDocument() {
        final Alias alias = new FakeCreditCardNumber();

        final AliasDocument document = converter.toDocument(alias);

        assertThat(document.getType()).isEqualTo(alias.getType());
    }

    @Test
    void shouldPopulateValueOnDocument() {
        final Alias alias = new FakeCreditCardNumber();

        final AliasDocument document = converter.toDocument(alias);

        assertThat(document.getValue()).isEqualTo(alias.getValue());
    }

}
