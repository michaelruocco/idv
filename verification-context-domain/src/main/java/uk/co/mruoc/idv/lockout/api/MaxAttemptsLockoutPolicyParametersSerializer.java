package uk.co.mruoc.idv.lockout.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.mruoc.idv.lockout.domain.model.MaxAttemptsLockoutPolicyParameters;

import java.io.IOException;
import java.util.Collection;

public class MaxAttemptsLockoutPolicyParametersSerializer extends StdSerializer<MaxAttemptsLockoutPolicyParameters> {

    MaxAttemptsLockoutPolicyParametersSerializer() {
        super(MaxAttemptsLockoutPolicyParameters.class);
    }

    @Override
    public void serialize(final MaxAttemptsLockoutPolicyParameters parameters,
                          final JsonGenerator json,
                          final SerializerProvider provider) throws IOException {
        toJson(parameters, json);
    }

    private void toJson(final MaxAttemptsLockoutPolicyParameters parameters,
                        final JsonGenerator json) throws IOException {
        json.writeStartObject();
        json.writeStringField("id", parameters.getId().toString());
        json.writeStringField("type", parameters.getLockoutType());
        json.writeNumberField("maxNumberOfAttempts", parameters.getMaxNumberOfAttempts());
        json.writeStringField("recordAttemptStrategy", parameters.getRecordAttemptStrategyType());
        writeStringArray(json, "channelIds", parameters.getChannelIds());
        writeStringArray(json, "activityNames", parameters.getActivityNames());
        writeStringArray(json, "aliasTypes", parameters.getAliasTypes());
        json.writeEndObject();
    }

    private static void writeStringArray(final JsonGenerator json,
                                         final String name,
                                         final Collection<String> values) throws IOException {
        json.writeFieldName(name);
        json.writeStartArray();
        for (final String channelId : values) {
            json.writeString(channelId);
        }
        json.writeEndArray();
    }

}
