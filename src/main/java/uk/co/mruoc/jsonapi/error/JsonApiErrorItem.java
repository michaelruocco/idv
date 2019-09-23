package uk.co.mruoc.jsonapi.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class JsonApiErrorItem {

    private final UUID id;
    private final int status;
    private final String title;
    private final String detail;
    private final Map<String, Object> meta;

}
