package uk.co.mruoc.idv.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.mruoc.idv.identity.dao.IdentityDao;
import uk.co.mruoc.idv.identity.dao.InMemoryIdentityDao;
import uk.co.mruoc.idv.lockout.dao.InMemoryVerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.verificationcontext.dao.InMemoryVerificationContextDao;
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

}
