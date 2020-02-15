package uk.co.idv.json.lockout.policy.nonlocking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class NonLockingLockoutPolicySerializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldSerializePolicy() throws JsonProcessingException {
        final LockoutPolicy policy = LockoutPolicyMother.nonLockingPolicy();

        final String json = MAPPER.writeValueAsString(policy);

        final String expectedJson = ContentLoader.loadContentFromClasspath("lockout/policy/nonlocking/non-locking-lockout-policy.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
