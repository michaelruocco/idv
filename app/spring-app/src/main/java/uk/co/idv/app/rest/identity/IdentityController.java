package uk.co.idv.app.rest.identity;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.api.identity.IdentityDocument;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasFactory;
import uk.co.idv.domain.entities.identity.alias.IdvId;
import uk.co.idv.domain.usecases.identity.IdentityService;
import uk.co.idv.domain.usecases.identity.LoadIdentityRequest;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class IdentityController {

    private final AliasFactory aliasFactory;
    private final IdentityService service;

    @GetMapping("/identities")
    public IdentityDocument getIdentity(@RequestParam final String aliasType,
                                        @RequestParam final String aliasValue) {
        final Alias alias = aliasFactory.build(aliasType, aliasValue);
        return loadIdentity(alias);
    }

    @GetMapping("/identities/{id}")
    public IdentityDocument getIdentity(@PathVariable("id") final UUID id) {
        final Alias alias = aliasFactory.build(IdvId.TYPE, id.toString());
        return loadIdentity(alias);
    }

    private IdentityDocument loadIdentity(final Alias alias) {
        final LoadIdentityRequest request = toRequest(alias);
        final Identity identity = service.load(request);
        return toDocument(identity);
    }

    private LoadIdentityRequest toRequest(final Alias alias) {
        return LoadIdentityRequest.builder()
                .alias(alias)
                .build();
    }

    private static IdentityDocument toDocument(final Identity identity) {
        return new IdentityDocument(identity);
    }

}
