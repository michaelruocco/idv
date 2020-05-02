package uk.co.idv.json.lockout.policy.hard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.assertion.LockoutAssertions;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.json.lockout.policy.LockoutPolicyObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

class HardLockoutPolicyJsonNodeConverterTest {

    private static final ObjectMapper MAPPER = new LockoutPolicyObjectMapperFactory().build();

    @Test
    void shouldDeserializePolicy() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("lockout/policy/hard/hard-lockout-policy.json");

        final LockoutPolicy policy = MAPPER.readValue(json, LockoutPolicy.class);

        final LockoutPolicy expectedPolicy = LockoutPolicyMother.hardLockoutPolicy();
        assertThat(policy).isEqualToIgnoringGivenFields(expectedPolicy, "level", "attemptFilter");
        LockoutAssertions.assertThat(policy.getLevel()).isEqualTo(expectedPolicy.getLevel());
    }

}
