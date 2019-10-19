package uk.co.mruoc.idv.mongo.dao.activity;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ActivityDocument {

    private final String name;
    private final String timestamp;
    private final Map<String, String> properties;

}
