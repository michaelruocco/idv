package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import lombok.Builder;
import lombok.Getter;



@Getter
@Builder
public class EligibilityDocument {

    final boolean eligible;
    final String reason;

}
