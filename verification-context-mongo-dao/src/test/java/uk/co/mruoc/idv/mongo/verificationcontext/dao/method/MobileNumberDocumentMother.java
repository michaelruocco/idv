package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

public class MobileNumberDocumentMother {

    public static MobileNumberDocument primary() {
        final MobileNumberDocument document = new MobileNumberDocument();
        document.setId("6c880ce6-0d3c-4ac7-b419-8c2dce645cfa");
        document.setNumber("07819389980");
        return document;
    }

    public static MobileNumberDocument secondary() {
        final MobileNumberDocument document = new MobileNumberDocument();
        document.setId("543185d6-0048-484e-8950-52c74d2124af");
        document.setNumber("07809374470");
        return document;
    }

}
