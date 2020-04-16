package uk.co.idv.app.rest.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.api.identity.IdentityDocument;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasFactory;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.identity.alias.IdvId;
import uk.co.idv.domain.usecases.identity.FakeIdentityService;
import uk.co.idv.domain.usecases.identity.LoadIdentityRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IdentityControllerTest {

    private final AliasFactory aliasFactory = mock(AliasFactory.class);
    private final Identity identity = IdentityMother.build();

    private final FakeIdentityService service = new FakeIdentityService(identity);

    private final IdentityController controller = new IdentityController(aliasFactory, service);

    @Test
    void shouldBuildAliasAndPassInLoadIdentityRequest() {
        final Alias alias = AliasesMother.debitCardNumber();
        given(aliasFactory.build(alias.getType(), alias.getValue())).willReturn(alias);

        controller.getIdentity(alias.getType(), alias.getValue());

        final LoadIdentityRequest request = service.getLastLoadRequest();
        assertThat(request.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldBuildIdvIdAliasAndPassInLoadIdentityRequest() {
        final UUID id = UUID.randomUUID();
        final Alias idvId = new IdvId(id);
        given(aliasFactory.build(IdvId.TYPE, id.toString())).willReturn(idvId);

        controller.getIdentity(id);

        final LoadIdentityRequest request = service.getLastLoadRequest();
        assertThat(request.getAlias()).isEqualTo(idvId);
    }

    @Test
    void shouldReturnLoadedIdentityInIdentityDocument() {
        final UUID id = UUID.randomUUID();

        final IdentityDocument document = controller.getIdentity(id);

        assertThat(document.getAttributes()).isEqualTo(identity);
    }

}
