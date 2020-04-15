package uk.co.idv.json.activity.converter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SingleSimpleActivityJsonNodeConverter extends AbstractActivityJsonNodeConverter {

    private final String supportedName;

    @Override
    public boolean supportsActivity(final String name) {
        boolean supported = supportedName.equals(name);
        log.info("returning supported {} for activity name {}", supported, name);
        return supported;
    }

}
