package uk.co.idv.json.verificationcontext.method;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.Duration;

public class DurationSerializer extends StdSerializer<Duration> {

    public DurationSerializer() {
        super(Duration.class);
    }

    @Override
    public void serialize(final Duration duration,
                          final JsonGenerator json,
                          final SerializerProvider provider) throws IOException {
        json.writeNumber(duration.toMillis());
    }

}
