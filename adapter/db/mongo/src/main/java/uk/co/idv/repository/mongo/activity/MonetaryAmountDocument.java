package uk.co.idv.repository.mongo.activity;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class MonetaryAmountDocument {

    private BigDecimal number;
    private String currencyCode;

}
