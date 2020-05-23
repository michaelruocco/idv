package uk.co.idv.app.config.domain.verificationcontext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.config.uk.domain.UkIdentityConfig;
import uk.co.idv.domain.entities.identity.alias.AliasFactory;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.usecases.identity.IdentityService;

@Configuration
public class IdentityConfig {

    private final UkIdentityConfig config = new UkIdentityConfig();

    @Bean
    public AliasFactory aliasFactory() {
        return config.aliasFactory();
    }

    @Bean
    public IdentityService identityService(final IdentityDao dao) {
        return config.identityService(dao);
    }

}
