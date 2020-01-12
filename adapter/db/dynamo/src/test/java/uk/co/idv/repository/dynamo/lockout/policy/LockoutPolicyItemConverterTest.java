package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.document.Item;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.repository.dynamo.json.JsonConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutPolicyItemConverterTest {

    private final JsonConverter jsonConverter = mock(JsonConverter.class);

    private final LockoutPolicyItemConverter converter = new LockoutPolicyItemConverter(jsonConverter);

    @Test
    void shouldSetIdOnItem() {
        final LockoutPolicy policy = LockoutPolicyMother.nonLockingPolicy();
        given(jsonConverter.toJson(policy)).willReturn("{}");

        final Item item = converter.toItem(policy);

        assertThat(item.getString("id")).isEqualTo(policy.getId().toString());
    }

    @Test
    void shouldSetChannelIdOnItem() {
        final LockoutPolicy policy = LockoutPolicyMother.nonLockingPolicy();
        given(jsonConverter.toJson(policy)).willReturn("{}");

        final Item item = converter.toItem(policy);

        assertThat(item.getString("channelId")).isEqualTo(policy.getChannelId());
    }

    @Test
    void shouldSetLockoutTypeOnItem() {
        final LockoutPolicy policy = LockoutPolicyMother.nonLockingPolicy();
        given(jsonConverter.toJson(policy)).willReturn("{}");

        final Item item = converter.toItem(policy);

        assertThat(item.getString("lockoutType")).isEqualTo(policy.getLockoutType());
    }

    @Test
    void shouldSetJsonBodyOnItem() {
        final LockoutPolicy policy = LockoutPolicyMother.nonLockingPolicy();
        final String expectedJson = "{}";
        given(jsonConverter.toJson(policy)).willReturn(expectedJson);

        final Item item = converter.toItem(policy);

        assertThat(item.getJSON("body")).isEqualTo(expectedJson);
    }

    @Test
    void shouldConvertJsonBodyToLockoutPolicy() {
        final String json = "{}";
        final Item item = new Item().withJSON("body", json);
        final LockoutPolicy expectedPolicy = LockoutPolicyMother.nonLockingPolicy();
        given(jsonConverter.toObject(json, LockoutPolicy.class)).willReturn(expectedPolicy);

        final LockoutPolicy policy = converter.toPolicy(item);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

}
