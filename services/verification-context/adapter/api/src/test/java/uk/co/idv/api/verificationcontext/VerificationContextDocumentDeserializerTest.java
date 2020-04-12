package uk.co.idv.api.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.Module;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.VerificationContextMother;
import uk.co.idv.json.activity.AllowAllSimpleActivityModule;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationContextDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(modules()).build();

    @Test
    void shouldDeserializeDocument() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/verification-context-document.json");

        final VerificationContextDocument document = MAPPER.readValue(json, VerificationContextDocument.class);

        final VerificationContextDocument expectedDocument = new VerificationContextDocument(VerificationContextMother.fake());
        assertThat(document.getAttributes()).isEqualToComparingFieldByField(expectedDocument.getAttributes());
    }

    private static Collection<Module> modules() {
        return Arrays.asList(
                new ApiVerificationContextModule(),
                new AllowAllSimpleActivityModule()
        );
    }

}
