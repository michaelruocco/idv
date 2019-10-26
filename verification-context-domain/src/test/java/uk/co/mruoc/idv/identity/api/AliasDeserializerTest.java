package uk.co.mruoc.idv.identity.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.identity.api.AliasDeserializer.AliasNotSupportedException;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.AliasesMother;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AliasDeserializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

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
                .isInstanceOf(AliasNotSupportedException.class)
                .hasMessage("not-supported-alias");
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new IdentityModule());
        return mapper;
    }

}
