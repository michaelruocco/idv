package uk.co.idv.app.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.repository.inmemory.identity.InMemoryIdentityDao;
import uk.co.idv.repository.inmemory.lockout.InMemoryLockoutPolicyDao;
import uk.co.idv.repository.inmemory.lockout.InMemoryVerificationAttemptsDao;
import uk.co.idv.repository.inmemory.verificationcontext.InMemoryVerificationContextDao;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.VerificationAttemptsDao;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyParametersConverter;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;

@Configuration
@EnableAutoConfiguration(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class
})
@Profile("stub")
public class InMemoryDaoConfig {

    @Bean
    public VerificationContextDao verificationContextDao() {
        return new InMemoryVerificationContextDao();
    }

    @Bean
    public VerificationAttemptsDao verificationAttemptsDao() {
        return new InMemoryVerificationAttemptsDao();
    }

    @Bean
    public IdentityDao identityDao() {
        return new InMemoryIdentityDao();
    }

    @Bean
    public LockoutPolicyDao lockoutPolicyDao(final LockoutPolicyParametersConverter parametersConverter) {
        return new InMemoryLockoutPolicyDao(parametersConverter);
    }

}
