package uk.co.idv.domain.usecases.onetimepasscode.verify;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.onetimepasscode.verify.VerifyOneTimePasscodeRequest.VerifyOneTimePasscodeRequestBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VerifyOneTimePasscodeRequestTest {

    private final VerifyOneTimePasscodeRequestBuilder builder = VerifyOneTimePasscodeRequest.builder();

    @Test
    void shouldReturnContextId() {
        final UUID id = UUID.randomUUID();

        final VerifyOneTimePasscodeRequest request = builder.id(id).build();

        assertThat(request.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnProvidedAlias() {
        final Collection<String> passcodes = Collections.singleton("12345678");

        final VerifyOneTimePasscodeRequest request = builder.passcodes(passcodes).build();

        assertThat(request.getPasscodes()).isEqualTo(passcodes);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(VerifyOneTimePasscodeRequest.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
