package uk.co.idv.repository.dynamo.lockout.policy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import uk.co.idv.domain.entities.lockout.assertion.LockoutAssertions;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.idv.repository.dynamo.DynamoConfig;
import uk.co.idv.repository.dynamo.DynamoDbLocalContainer;
import uk.co.idv.repository.dynamo.json.JacksonJsonConverter;
import uk.co.idv.repository.dynamo.json.JsonConverter;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class LockoutPolicyDaoTest {

    @Container
    public static final DynamoDbLocalContainer DYNAMO_DB = new DynamoDbLocalContainer();

    private LockoutPolicyDao dao;

    @BeforeEach
    void setUp() {
        final DynamoConfig config = DYNAMO_DB.getConfig();
        final JsonConverter jsonConverter = new JacksonJsonConverter(ObjectMapperSingleton.instance());
        dao = config.lockoutPolicyDao(jsonConverter);
    }

    @Test
    void shouldLoadLockoutPolicyById() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        dao.save(policy);
        final Optional<LockoutPolicy> loadedPolicy = dao.load(policy.getId());

        assertThat(loadedPolicy.isPresent()).isTrue();
        assertThat(loadedPolicy.get()).isEqualToIgnoringGivenFields(policy, "level");
        LockoutAssertions.assertThat(loadedPolicy.get().getLockoutLevel()).isEqualTo(policy.getLockoutLevel());
    }

    @Test
    void shouldLoadEmptyOptionalIfPolicyIsNotSaved() {
        final Optional<LockoutPolicy> loadedPolicy = dao.load(UUID.randomUUID());

        assertThat(loadedPolicy).isEmpty();
    }

}
