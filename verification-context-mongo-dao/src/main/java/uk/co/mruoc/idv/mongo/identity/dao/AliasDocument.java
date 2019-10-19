package uk.co.mruoc.idv.mongo.identity.dao;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of = { "type", "value" })
@Getter
@Builder
public class AliasDocument {

    private final String type;
    private final String value;

}
