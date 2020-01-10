package uk.co.idv.repository.dynamo.lockout.attempt;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.repository.dynamo.json.JsonConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DynamoVerificationAttemptsDaoTest {

    private final JsonConverter converter = mock(JsonConverter.class);
    private final Table table = mock(Table.class);
    private final DynamoVerificationAttemptsDao dao = DynamoVerificationAttemptsDao.builder()
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

}
