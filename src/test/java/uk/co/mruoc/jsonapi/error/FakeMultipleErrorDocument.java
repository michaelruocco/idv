package uk.co.mruoc.jsonapi.error;

class FakeMultipleErrorDocument extends JsonApiMultipleErrorDocument {

    FakeMultipleErrorDocument() {
        super(new FakeBadRequestErrorItem(),
                new FakeNotFoundErrorItem()
        );
    }

}
