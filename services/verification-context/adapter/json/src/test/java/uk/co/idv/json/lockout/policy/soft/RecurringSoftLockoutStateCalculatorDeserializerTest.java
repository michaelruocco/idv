package uk.co.idv.json.lockout.policy.soft;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.policy.assertion.PolicyAssertions;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.json.lockout.policy.LockoutPolicyObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

class RecurringSoftLockoutStateCalculatorDeserializerTest {

    private static final ObjectMapper MAPPER = new LockoutPolicyObjectMapperFactory().build();

    @Test
    void shouldDeserializePolicy() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("lockout/policy/soft/recurring-soft-lockout-policy.json");

        final LockoutPolicy policy = MAPPER.readValue(json, LockoutPolicy.class);

        final LockoutPolicy expectedPolicy = LockoutPolicyMother.recurringSoftLockoutPolicy();
        assertThat(policy).isEqualToIgnoringGivenFields(expectedPolicy, "level", "attemptFilter");
        PolicyAssertions.assertThat(policy.getLevel()).isEqualTo(expectedPolicy.getLevel());
    }

}
