package uk.co.mruoc.jsonapi.fake;

import uk.co.mruoc.jsonapi.JsonApiDocumentWithId;

public class FakeJsonApiDocumentWithId extends JsonApiDocumentWithId<FakeAttributes> {

    public FakeJsonApiDocumentWithId() {
        this("fake-attributes-type", new FakeAttributes());
    }

    public FakeJsonApiDocumentWithId(final String type, final FakeAttributes attributes) {
        super(attributes.getId(), type, attributes);
    }

}
