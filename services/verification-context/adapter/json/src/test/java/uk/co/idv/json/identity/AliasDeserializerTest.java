package uk.co.idv.json.identity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AliasDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new AliasModule()).build();

    @Test
    void shouldDeserializeCreditCard() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("identity/credit-card-alias.json");

        final Alias alias = MAPPER.readValue(json, Alias.class);

        assertThat(alias).isEqualTo(AliasesMother.creditCardNumber());
    }

    @Test
    void shouldDeserializeDebitCard() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("identity/debit-card-alias.json");

        final Alias alias = MAPPER.readValue(json, Alias.class);

        assertThat(alias).isEqualTo(AliasesMother.debitCardNumber());
    }

    @Test
    void shouldDeserializeIdvId() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("identity/idv-id-alias.json");

        final Alias alias = MAPPER.readValue(json, Alias.class);

        assertThat(alias).isEqualTo(AliasesMother.idvId());
    }

    @Test
    void shouldThrowExceptionIfAliasNotSupported() {
        final String json = ContentLoader.loadContentFromClasspath("identity/not-supported-alias.json");

        final Throwable error = catchThrowable(() -> MAPPER.readValue(json, Alias.class));

        assertThat(error)
                .isInstanceOf(AliasDeserializer.AliasNotSupportedException.class)
                .hasMessage("not-supported-alias");
    }

}
