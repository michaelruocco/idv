package uk.co.idv.json.lockout.policy.soft;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.assertion.LockoutAssertions;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.json.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

class RecurringSoftLockoutPolicyJsonNodeConverterTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory().build();

    @Test
    void shouldDeserializePolicy() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("lockout/policy/soft/recurring-soft-lockout-policy.json");

        final LockoutPolicy policy = MAPPER.readValue(json, LockoutPolicy.class);

        final LockoutPolicy expectedPolicy = LockoutPolicyMother.recurringSoftLockoutPolicy();
        assertThat(policy).isEqualToIgnoringGivenFields(expectedPolicy, "level");
        LockoutAssertions.assertThat(policy.getLockoutLevel()).isEqualTo(expectedPolicy.getLockoutLevel());
    }

}
