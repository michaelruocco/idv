package uk.co.idv.domain.usecases.verification.onetimepasscode;

public interface PasscodeGenerator {

    String generate(final int length);

}
