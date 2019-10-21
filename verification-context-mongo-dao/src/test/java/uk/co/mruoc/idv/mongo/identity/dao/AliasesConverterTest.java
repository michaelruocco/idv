package uk.co.mruoc.idv.mongo.identity.dao;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.FakeIdvId;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AliasesConverterTest {

    private final AliasDocument document1 = new FakeIdvIdAliasDocument();
    private final AliasDocument document2 = new FakeCreditCardNumberAliasDocument();

    private final Alias alias1 = new FakeIdvId();
    private final Alias alias2 = new FakeCreditCardNumber();

    private final AliasConverter aliasConverter = mock(AliasConverter.class);

    private final AliasesConverter aliasesConverter = new AliasesConverter(aliasConverter);

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
