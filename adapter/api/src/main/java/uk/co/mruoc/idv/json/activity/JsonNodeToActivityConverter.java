package uk.co.mruoc.idv.json.activity;

import com.fasterxml.jackson.databind.JsonNode;
import uk.co.mruoc.idv.domain.model.activity.Activity;

public interface JsonNodeToActivityConverter {

    Activity toActivity(final JsonNode node);

}
