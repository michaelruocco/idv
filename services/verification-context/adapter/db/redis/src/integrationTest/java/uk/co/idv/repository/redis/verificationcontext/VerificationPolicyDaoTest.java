package uk.co.idv.repository.redis.verificationcontext;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import uk.co.idv.domain.entities.policy.DefaultPolicyRequest;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.entities.policy.assertion.PolicyAssertions;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicyMother;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyDao;
import uk.co.idv.json.verificationcontext.VerificationContextModule;
import uk.co.idv.utils.json.converter.JsonConverter;
import uk.co.idv.utils.json.converter.jackson.JacksonJsonConverter;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class VerificationPolicyDaoTest {

    @Container
    public static final RedissonLocalContainer REDIS = new RedissonLocalContainer();

    private VerificationPolicyDao dao;

    @BeforeEach
    void setUp() {
        final ObjectMapperFactory factory = new ObjectMapperFactory(new VerificationContextModule());
        final JsonConverter jsonConverter = new JacksonJsonConverter(factory.build());
        final VerificationContextRedisConfig config = new VerificationContextRedisConfig(REDIS.buildClient());
        dao = config.verificationPolicyDao(jsonConverter);
    }

    @Test
    void shouldLoadLockoutPolicyById() {
        final VerificationPolicy policy = VerificationPolicyMother.build();
        dao.save(policy);
        final Optional<VerificationPolicy> loadedPolicy = dao.load(policy.getId());

        assertThat(loadedPolicy.isPresent()).isTrue();
        assertThat(loadedPolicy.get()).isEqualToIgnoringGivenFields(policy, "level", "attemptFilter");
        PolicyAssertions.assertThat(loadedPolicy.get().getLevel()).isEqualTo(policy.getLevel());
    }

    @Test
    void shouldLoadLockoutPolicyByRequest() {
        final VerificationPolicy policy = VerificationPolicyMother.build();
        dao.save(policy);
        final PolicyLevel level = policy.getLevel();
        final PolicyRequest request = DefaultPolicyRequest.builder()
                .channelId(level.getChannelId())
                .activityName(IterableUtils.get(level.getActivityNames(), 0))
                .aliasType(IterableUtils.get(level.getAliasTypes(), 0))
                .build();

        final Collection<VerificationPolicy> loadedPolicies = dao.load(request);

        assertThat(loadedPolicies).hasSize(1);
    }

    @Test
    void shouldLoadAllLockoutPolicies() {
        final VerificationPolicy policy = VerificationPolicyMother.build();
        dao.save(policy);

        final Collection<VerificationPolicy> loadedPolicies = dao.load();

        assertThat(loadedPolicies).hasSize(1);
    }

    @Test
    void shouldLoadEmptyOptionalIfPolicyIsNotSaved() {
        final Optional<VerificationPolicy> loadedPolicy = dao.load(UUID.randomUUID());

        assertThat(loadedPolicy).isEmpty();
    }

}
