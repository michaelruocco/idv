package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.mruoc.idv.verificationcontext.domain.service.CreateContextRequest;

import java.io.IOException;

public class CreateContextRequestDeserializer extends StdDeserializer<CreateContextRequest> {

    protected CreateContextRequestDeserializer() {
        super(CreateContextRequest.class);
    }

    @Override
    public CreateContextRequest deserialize(final JsonParser parser, final DeserializationContext context) throws IOException, JsonProcessingException {
        return null;
    }

}
