package uk.co.idv.json.lockout.policy.soft;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class SoftLockoutPolicySerializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldSerializePolicy() throws JsonProcessingException {
        final LockoutPolicy policy = LockoutPolicyMother.softLockoutPolicy();

        final String json = MAPPER.writeValueAsString(policy);
        System.out.println(json);

        final String expectedJson = ContentLoader.loadContentFromClasspath("lockout/policy/soft-lockout-policy.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
