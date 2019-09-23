package uk.co.mruoc.jsonapi.error;

public class InternalServerErrorDocument extends JsonApiSingleErrorDocument {

    public InternalServerErrorDocument(final String detail) {
        super(new InternalServerErrorItem(detail));
    }

}
