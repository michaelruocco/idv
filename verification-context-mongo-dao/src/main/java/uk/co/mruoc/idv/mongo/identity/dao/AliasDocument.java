package uk.co.mruoc.idv.mongo.identity.dao;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(of = { "type", "value" })
@Getter
@Builder
public class AliasDocument {

    private final String type;
    private final String value;

}
