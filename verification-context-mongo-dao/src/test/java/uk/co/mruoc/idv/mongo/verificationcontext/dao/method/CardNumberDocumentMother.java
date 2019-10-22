package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

public class CardNumberDocumentMother {

    public static CardNumberDocument credit() {
        final CardNumberDocument document = common();
        document.setType("CREDIT");
        return document;
    }

    public static CardNumberDocument debit() {
        final CardNumberDocument document = common();
        document.setType("DEBIT");
        return document;
    }

    public static CardNumberDocument invalidType() {
        final CardNumberDocument document = common();
        document.setType("INVALID");
        return document;
    }

    private static CardNumberDocument common() {
        final CardNumberDocument document = new CardNumberDocument();
        document.setId("6c880ce6-0d3c-4ac7-b419-8c2dce645cfa");
        document.setTokenized("4929991234567890");
        return document;
    }

}
