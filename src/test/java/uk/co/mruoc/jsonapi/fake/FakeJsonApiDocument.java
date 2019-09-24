package uk.co.mruoc.jsonapi.fake;

import uk.co.mruoc.jsonapi.JsonApiDocument;

public class FakeJsonApiDocument extends JsonApiDocument<FakeAttributes> {

    public FakeJsonApiDocument() {
        this("fake-attributes-type", new FakeAttributes());
    }

    FakeJsonApiDocument(final String type, final FakeAttributes attributes) {
        super(type, attributes);
    }

}
