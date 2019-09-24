package uk.co.mruoc.jsonapi.error;

class FakeSingleErrorDocument extends JsonApiSingleErrorDocument {

    FakeSingleErrorDocument() {
        super(new FakeInternalServerErrorItem());
    }

}
