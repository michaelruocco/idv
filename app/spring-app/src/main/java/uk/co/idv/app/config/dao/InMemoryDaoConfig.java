package uk.co.idv.app.config.dao;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationDao;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyDao;
import uk.co.idv.repository.inmemory.identity.InMemoryIdentityDao;
import uk.co.idv.repository.inmemory.lockout.InMemoryLockoutPolicyDao;
import uk.co.idv.repository.inmemory.lockout.InMemoryVerificationAttemptDao;
import uk.co.idv.repository.inmemory.onetimepasscode.InMemoryOneTimePasscodeVerificationDao;
import uk.co.idv.repository.inmemory.verificationcontext.InMemoryVerificationContextDao;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptDao;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.repository.inmemory.verificationcontext.InMemoryVerificationPolicyDao;

@Configuration
@EnableAutoConfiguration(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class
})
@Profile("stubbed")
public class InMemoryDaoConfig {

    @Bean
    public VerificationContextDao verificationContextDao() {
        return new InMemoryVerificationContextDao();
    }

    @Bean
    public VerificationAttemptDao verificationAttemptsDao() {
        return new InMemoryVerificationAttemptDao();
    }

    @Bean
    public IdentityDao identityDao() {
        return new InMemoryIdentityDao();
    }

    @Bean
    public LockoutPolicyDao lockoutPolicyDao() {
        return new InMemoryLockoutPolicyDao();
    }

    @Bean
    public VerificationPolicyDao verificationPolicyDao() {
        return new InMemoryVerificationPolicyDao();
    }

    @Bean
    public OneTimePasscodeVerificationDao oneTimePasscodeVerificationDao() {
        return new InMemoryOneTimePasscodeVerificationDao();
    }

}
