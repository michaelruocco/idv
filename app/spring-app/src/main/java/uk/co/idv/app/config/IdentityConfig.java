package uk.co.idv.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.config.uk.UkIdentityConfig;
import uk.co.idv.domain.entities.identity.alias.AliasFactory;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.usecases.identity.IdentityService;

@Configuration
public class IdentityConfig {

    private final UkIdentityConfig ukIdentityConfig = new UkIdentityConfig();

    @Bean
    public AliasFactory aliasFactory() {
        return ukIdentityConfig.aliasFactory();
    }

    @Bean
    public IdentityService identityService(final IdentityDao dao) {
        return ukIdentityConfig.identityService(dao);
    }

}
