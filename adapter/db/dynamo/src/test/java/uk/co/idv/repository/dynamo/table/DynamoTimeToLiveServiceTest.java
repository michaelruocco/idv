package uk.co.idv.repository.dynamo.table;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveResult;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class DynamoTimeToLiveServiceTest {

    private final AmazonDynamoDB client = mock(AmazonDynamoDB.class);

    private final DynamoTimeToLiveService service = new DynamoTimeToLiveService(client);

    @Test
    void shouldUpdateTimeToLive() {
        final String tableName = "table-name";
        final UpdateTimeToLiveRequest request = new IdvTimeToLiveRequest(tableName);
        final UpdateTimeToLiveResult expectedResult = new UpdateTimeToLiveResult();
        given(client.updateTimeToLive(request)).willReturn(expectedResult);

        final Optional<UpdateTimeToLiveResult> result = service.updateTimeToLive(request);

        assertThat(result).contains(expectedResult);
    }

    @Test
    void shouldNotThrowExceptionIfTimeToLiveAlreadyEnabled() {
        final String tableName = "table-name";
        final UpdateTimeToLiveRequest request = new IdvTimeToLiveRequest(tableName);
        given(client.updateTimeToLive(request)).willThrow(new AmazonDynamoDBException("TimeToLive is already enabled"));

        final Optional<UpdateTimeToLiveResult> result = service.updateTimeToLive(request);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldThrowAnyOtherAmazonDynamoDBException() {
        final String tableName = "table-name";
        final UpdateTimeToLiveRequest request = new IdvTimeToLiveRequest(tableName);
        final Throwable expectedError = new AmazonDynamoDBException("any-message");
        given(client.updateTimeToLive(request)).willThrow(expectedError);

        final Throwable error = catchThrowable(() -> service.updateTimeToLive(request));

        assertThat(error).isEqualTo(expectedError);
    }

}
