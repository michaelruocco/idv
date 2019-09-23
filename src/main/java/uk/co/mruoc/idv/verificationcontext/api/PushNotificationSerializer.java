package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;

import java.io.IOException;

public class PushNotificationSerializer extends StdSerializer<PushNotification> {

    PushNotificationSerializer() {
        super(PushNotification.class);
    }

    @Override
    public void serialize(final PushNotification method,
                          final JsonGenerator json,
                          final SerializerProvider provider) throws IOException {
        if (method.isEligible()) {
            writeEligibleJson(method, json);
            return;
        }
        writeIneligibleJson(method, json);
    }

    private void writeEligibleJson(final PushNotification method,
                                   final JsonGenerator json) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        JsonFieldWriter.writeDuration(method.getDuration(), json);
        json.writeEndObject();
    }

    private void writeIneligibleJson(final PushNotification method,
                                     final JsonGenerator json) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        json.writeEndObject();
    }

    private void writeCommonFields(final PushNotification method,
                                   final JsonGenerator json) throws IOException {
        JsonFieldWriter.writeName(method.getName(), json);
        JsonFieldWriter.writeEligibility(method.getEligibility(), json);
    }

}
