package uk.co.mruoc.idv.mongo.dao.activity;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class MonetaryAmountDocument {

    private BigDecimal number;
    private String currencyCode;

}
