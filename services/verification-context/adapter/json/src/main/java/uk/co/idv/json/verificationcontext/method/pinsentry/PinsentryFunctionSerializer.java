package uk.co.idv.json.verificationcontext.method.pinsentry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;

import java.io.IOException;

public class PinsentryFunctionSerializer extends StdSerializer<PinsentryFunction> {

    public PinsentryFunctionSerializer() {
        super(PinsentryFunction.class);
    }

    @Override
    public void serialize(final PinsentryFunction function,
                          final JsonGenerator json,
                          final SerializerProvider provider) throws IOException {
        json.writeString(function.name().toLowerCase());
    }

}
