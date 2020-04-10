package uk.co.idv.repository.dynamo.lockout.policy;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import uk.co.idv.domain.entities.lockout.DefaultLockoutPolicyRequest;
import uk.co.idv.domain.entities.lockout.LockoutPolicyRequest;
import uk.co.idv.domain.entities.lockout.assertion.LockoutAssertions;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;
import uk.co.idv.dynamo.test.DynamoDbLocalContainer;
import uk.co.idv.json.VerificationContextModuleProvider;
import uk.co.idv.repository.dynamo.DynamoConfig;
import uk.co.idv.utils.json.jackson.JacksonJsonConverter;
import uk.co.idv.utils.json.converter.JsonConverter;
import uk.co.idv.utils.json.jackson.ObjectMapperFactory;

import java.util.Collection;
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
        final DynamoConfig config = new DynamoConfig(DYNAMO_DB.buildClient());
        final ObjectMapperFactory factory = new ObjectMapperFactory(new VerificationContextModuleProvider());
        final JsonConverter jsonConverter = new JacksonJsonConverter(factory.build());
        dao = config.lockoutPolicyDao(jsonConverter);
        config.clearLockoutPolicies();
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
    void shouldLoadLockoutPolicyByRequest() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        dao.save(policy);
        final LockoutLevel level = policy.getLockoutLevel();
        final LockoutPolicyRequest request = DefaultLockoutPolicyRequest.builder()
                .channelId(level.getChannelId())
                .activityName(IterableUtils.get(level.getActivityNames(), 0))
                .aliasType(IterableUtils.get(level.getAliasTypes(), 0))
                .build();

        final Collection<LockoutPolicy> loadedPolicies = dao.load(request);

        assertThat(loadedPolicies).hasSize(1);
    }

    @Test
    void shouldLoadAllLockoutPolicies() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        dao.save(policy);

        final Collection<LockoutPolicy> loadedPolicies = dao.load();

        assertThat(loadedPolicies).hasSize(1);
    }

    @Test
    void shouldLoadEmptyOptionalIfPolicyIsNotSaved() {
        final Optional<LockoutPolicy> loadedPolicy = dao.load(UUID.randomUUID());

        assertThat(loadedPolicy).isEmpty();
    }

}
