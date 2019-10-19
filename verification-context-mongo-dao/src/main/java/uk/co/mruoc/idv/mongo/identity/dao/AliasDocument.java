package uk.co.mruoc.idv.mongo.identity.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = { "type", "value" })
@Data
public class AliasDocument {

    private String type;
    private String value;

}
