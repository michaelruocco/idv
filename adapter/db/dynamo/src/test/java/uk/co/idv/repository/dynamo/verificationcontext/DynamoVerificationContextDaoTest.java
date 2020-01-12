package uk.co.idv.repository.dynamo.verificationcontext;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.FakeVerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DynamoVerificationContextDaoTest {

    private final VerificationContextItemConverter itemConverter = mock(VerificationContextItemConverter.class);
    private final Table table = mock(Table.class);

    private final DynamoVerificationContextDao dao = DynamoVerificationContextDao.builder()
            .itemConverter(itemConverter)
            .table(table)
            .build();

    @Test
    void shouldSaveContext() {
        final VerificationContext context = new FakeVerificationContext();
        final Item item = new Item();
        given(itemConverter.toItem(context)).willReturn(item);

        dao.save(context);

        verify(table).putItem(item);
    }

    @Test
    void shouldReturnContext() {
        final UUID id = UUID.randomUUID();
        final Item item = new Item();
        given(table.getItem("id", id.toString())).willReturn(item);
        final VerificationContext expectedContext = new FakeVerificationContext();
        given(itemConverter.toContext(item)).willReturn(expectedContext);

        final Optional<VerificationContext> context = dao.load(id);

        assertThat(context).contains(expectedContext);
    }

    @Test
    void shouldReturnEmptyOptionalIfLoadContextNotPresent() {
        final UUID id = UUID.randomUUID();
        given(table.getItem("id", id.toString())).willReturn(null);

        final Optional<VerificationContext> context = dao.load(id);

        assertThat(context).isEmpty();
    }

}
