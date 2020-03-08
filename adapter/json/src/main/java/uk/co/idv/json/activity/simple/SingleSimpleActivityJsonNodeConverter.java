package uk.co.idv.json.activity.simple;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.SimpleActivity;
import uk.co.idv.json.activity.ActivityJsonNodeConverter;

@Slf4j
@RequiredArgsConstructor
public class SingleSimpleActivityJsonNodeConverter implements ActivityJsonNodeConverter {

    private final String supportedName;

    @Override
    public boolean supportsActivity(final String name) {
        boolean supported = supportedName.equals(name);
        log.info("returning supported {} for activity name {}", supported, name);
        return supported;
    }

    @Override
    public Activity toActivity(final JsonNode node, final JsonParser parser, final DeserializationContext context) {
        final String name = ActivityJsonNodeConverter.extractName(node);
        log.info("converting {} to simple activity", name);
        return SimpleActivity.builder()
                .name(name)
                .timestamp(ActivityJsonNodeConverter.extractTimestamp(node))
                .build();
    }

}
