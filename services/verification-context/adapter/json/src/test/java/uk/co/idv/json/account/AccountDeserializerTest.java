package uk.co.idv.json.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.card.account.AccountMother;
import uk.co.idv.json.account.AccountDeserializer.InvalidAccountStatusException;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AccountDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new AccountModule()).build();

    @Test
    void shouldDeserializeOpenAccount() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("account/open-account.json");

        final Account account = MAPPER.readValue(json, Account.class);

        assertThat(account).isEqualTo(AccountMother.open());
    }

    @Test
    void shouldDeserializeClosedAccount() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("account/closed-account.json");

        final Account account = MAPPER.readValue(json, Account.class);

        assertThat(account).isEqualToComparingFieldByField(AccountMother.closed());
    }

    @Test
    void shouldThrowExceptionIfStatusIsInvalid() {
        final String json = ContentLoader.loadContentFromClasspath("account/invalid-account.json");

        final Throwable error = catchThrowable(() -> MAPPER.readValue(json, Account.class));

        assertThat(error)
                .isInstanceOf(InvalidAccountStatusException.class)
                .hasMessage("invalid");
    }

}
