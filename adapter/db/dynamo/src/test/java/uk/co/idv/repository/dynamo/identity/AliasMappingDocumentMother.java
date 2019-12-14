package uk.co.idv.repository.dynamo.identity;

public class AliasMappingDocumentMother {

    public static AliasMappingDocument build() {
        return build(0);
    }

    public static AliasMappingDocument build(final int number) {
        final AliasMappingDocument document = new AliasMappingDocument();
        document.setAlias("alias" + number);
        document.setIdvId("cfc54f11-6d36-4687-84b8-d64f81e8431b");
        return document;
    }

}
