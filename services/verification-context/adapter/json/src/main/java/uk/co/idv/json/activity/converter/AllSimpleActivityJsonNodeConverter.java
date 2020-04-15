package uk.co.idv.json.activity.converter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AllSimpleActivityJsonNodeConverter extends AbstractActivityJsonNodeConverter {

    @Override
    public boolean supportsActivity(final String name) {
        log.info("all activity names supported, returning true for {}", name);
        return true;

    }

}
