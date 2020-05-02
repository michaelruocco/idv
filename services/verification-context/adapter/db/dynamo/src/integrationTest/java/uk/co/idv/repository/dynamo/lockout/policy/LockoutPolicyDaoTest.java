package uk.co.idv.repository.dynamo.lockout.policy;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import uk.co.idv.domain.entities.policy.DefaultPolicyRequest;
import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.entities.lockout.assertion.LockoutAssertions;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyDao;
import uk.co.idv.dynamo.test.DynamoDbLocalContainer;
import uk.co.idv.json.lockout.policy.LockoutPolicyModule;
import uk.co.idv.repository.dynamo.VerificationContextDynamoConfig;
import uk.co.idv.utils.json.converter.jackson.JacksonJsonConverter;
import uk.co.idv.utils.json.converter.JsonConverter;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;

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
        final VerificationContextDynamoConfig config = new VerificationContextDynamoConfig(DYNAMO_DB.buildClient());
        final ObjectMapperFactory factory = new ObjectMapperFactory(new LockoutPolicyModule());
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
        assertThat(loadedPolicy.get()).isEqualToIgnoringGivenFields(policy, "level", "attemptFilter");
        LockoutAssertions.assertThat(loadedPolicy.get().getLevel()).isEqualTo(policy.getLevel());
    }

    @Test
    void shouldLoadLockoutPolicyByRequest() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        dao.save(policy);
        final PolicyLevel level = policy.getLevel();
        final PolicyRequest request = DefaultPolicyRequest.builder()
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
