package uk.co.idv.repository.dynamo.identity.alias;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasFactory;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AliasConverterTest {

    private final AliasFactory factory = mock(AliasFactory.class);

    private final AliasConverter converter = new AliasConverter(factory);

    @Test
    void shouldConvertAliasToString() {
        final Alias alias = AliasesMother.idvId();

        final String value = converter.toString(alias);

        final String expectedValue = toString(alias);
        assertThat(value).isEqualTo(expectedValue);
    }

    @Test
    void shouldPassAliasTypeAndValueToAliasFactoryToBuildAlias() {
        final Alias alias = AliasesMother.idvId();
        given(factory.build(alias.getType(), alias.getValue())).willReturn(alias);
        final String value = toString(alias);

        final Alias result = converter.toAlias(value);

        assertThat(result).isEqualTo(alias);
    }

    private static String toString(final Alias alias) {
        return String.format("%s|%s", alias.getType(), alias.getValue());
    }

}
