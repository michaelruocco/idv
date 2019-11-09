package uk.co.idv.repository.mongo.verificationcontext.method.pinsentry.physical;

import lombok.Data;


@Data
public class CardNumberDocument {

    private String id;
    private String tokenized;
    private String type;

}
