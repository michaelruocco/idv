package uk.co.idv.repository.dynamo.table;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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
    void shouldNotCreateTableIfTableAlreadyCreated() {
        final String tableName = "table-name";
        final CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName);
        final Table expectedTable = mock(Table.class);
        given(creator.create(request)).willReturn(expectedTable);
        service.getOrCreateTable(request);

        service.getOrCreateTable(request);

        verify(creator, times(1)).create(request);
    }

    @Test
    void shouldDeleteAndRecreateTableIfTableAlreadyCreated() {
        final String tableName = "table-name";
        final CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName);
        final Table expectedTable = mock(Table.class);
        given(creator.create(request)).willReturn(expectedTable);
        service.getOrCreateTable(request);

        service.recreateTable(request);

        final InOrder inOrder = Mockito.inOrder(expectedTable, creator);
        inOrder.verify(creator).create(request);
        inOrder.verify(expectedTable).delete();
        inOrder.verify(creator).create(request);
    }

    @Test
    void shouldNotDeleteIfTableDoesNotAlreadyExist() {
        final String tableName = "table-name";
        final CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName);
        final Table expectedTable = mock(Table.class);
        given(creator.create(request)).willReturn(expectedTable);

        service.recreateTable(request);

        verify(expectedTable, never()).delete();
        verify(creator).create(request);
    }

}
