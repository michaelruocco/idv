
package uk.co.idv.json.lockout.policy.soft;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;

import java.io.IOException;

public class SoftLockIntervalSerializer extends StdSerializer<SoftLockInterval> {

    public SoftLockIntervalSerializer() {
        super(SoftLockInterval.class);
    }

    @Override
    public void serialize(final SoftLockInterval interval, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        toJson(interval, json);
    }

    private void toJson(final SoftLockInterval interval, final JsonGenerator json) throws IOException {
        json.writeStartObject();
        json.writeNumberField("numberOfAttempts", interval.getNumberOfAttempts());
        json.writeNumberField("duration", interval.getDuration().toMillis());
        json.writeEndObject();
    }

}
