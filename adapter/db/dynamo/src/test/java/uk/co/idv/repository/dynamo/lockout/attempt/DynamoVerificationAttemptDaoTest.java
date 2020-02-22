package uk.co.idv.repository.dynamo.lockout.attempt;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.utils.json.converter.JsonConverter;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DynamoVerificationAttemptDaoTest {

    private final JsonConverter converter = mock(JsonConverter.class);
    private final Table table = mock(Table.class);
    private final DynamoVerificationAttemptDao dao = DynamoVerificationAttemptDao.builder()
            .converter(converter)
            .table(table)
            .build();

    @Test
    void shouldSaveVerificationAttemptsItemWithIdvIdAsPrimaryKey() {
        final VerificationAttempts attempts = VerificationAttemptsMother.oneAttempt();
        final String json = "{}";
        given(converter.toJson(attempts)).willReturn(json);

        dao.save(attempts);

        final ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
        verify(table).putItem(captor.capture());
        final Item savedItem = captor.getValue();
        assertThat(savedItem.get("id")).isEqualTo(attempts.getIdvId().toString());
    }

    @Test
    void shouldSaveVerificationAttemptsItemWithBodyJson() {
        final VerificationAttempts attempts = VerificationAttemptsMother.oneAttempt();
        final String json = "{}";
        given(converter.toJson(attempts)).willReturn(json);

        dao.save(attempts);

        final ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
        verify(table).putItem(captor.capture());
        final Item savedItem = captor.getValue();
        assertThat(savedItem.getJSON("body")).isEqualTo(json);
    }

    @Test
    void shouldLoadExistingVerificationAttemptsByIdvId() {
        final Item loadedItem = mock(Item.class);
        final String json = "{}";
        given(loadedItem.getJSON("body")).willReturn(json);
        final VerificationAttempts expectedAttempts = VerificationAttemptsMother.oneAttempt();
        given(table.getItem("id", expectedAttempts.getIdvId().toString())).willReturn(loadedItem);
        given(converter.toObject(json, VerificationAttempts.class)).willReturn(expectedAttempts);

        final Optional<VerificationAttempts> attempts = dao.load(expectedAttempts.getIdvId());

        assertThat(attempts).contains(expectedAttempts);
    }

    @Test
    void shouldReturnEmptyOptionalIfVerificationAttemptsDoNotExist() {
        final UUID idvId = UUID.randomUUID();
        given(table.getItem("id", idvId.toString())).willReturn(null);

        final Optional<VerificationAttempts> attempts = dao.load(idvId);

        assertThat(attempts).isEmpty();
    }

}
