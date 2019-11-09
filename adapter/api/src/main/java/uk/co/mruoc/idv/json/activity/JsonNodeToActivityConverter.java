package uk.co.mruoc.idv.json.activity;

import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.activity.Activity;

public interface JsonNodeToActivityConverter {

    Activity toActivity(final JsonNode node);

}
