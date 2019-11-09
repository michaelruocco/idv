package uk.co.mruoc.idv.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.mruoc.idv.identity.dao.IdentityDao;
import uk.co.idv.repository.inmemory.identity.InMemoryIdentityDao;
import uk.co.idv.repository.inmemory.lockout.InMemoryLockoutPolicyDao;
import uk.co.idv.repository.inmemory.lockout.InMemoryVerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.dao.LockoutPolicyDao;
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyParametersConverter;
import uk.co.idv.repository.inmemory.verificationcontext.InMemoryVerificationContextDao;
import uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao;

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
