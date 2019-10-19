package uk.co.mruoc.idv.mongo.dao.activity;

import lombok.Data;


@Data
public class MonetaryAmountDocument {

    private Number number;
    private String currencyCode;

}
