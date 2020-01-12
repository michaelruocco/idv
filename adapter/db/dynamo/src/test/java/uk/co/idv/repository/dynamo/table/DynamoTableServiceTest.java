package uk.co.idv.repository.dynamo.table;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DynamoTableServiceTest {

    private final DynamoTableCreator creator = mock(DynamoTableCreator.class);

    private final DynamoTableService service = new DynamoTableService(creator);

    @Test
    void shouldReturnCreatedTable() {
        final String tableName = "table-name";
        final CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName);
        final Table expectedTable = mock(Table.class);
        given(creator.create(request)).willReturn(expectedTable);

        final Table table = service.getOrCreateTable(request);

        assertThat(table).isEqualTo(expectedTable);
    }

    @Test
    void shouldReturnNotCreateTableIfTableAlreadyCreated() {
        final String tableName = "table-name";
        final CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName);
        final Table expectedTable = mock(Table.class);
        given(creator.create(request)).willReturn(expectedTable);
        service.getOrCreateTable(request);

        service.getOrCreateTable(request);

        verify(creator, times(1)).create(request);
    }

}
