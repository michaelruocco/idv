package uk.co.idv.domain.usecases.verification.onetimepasscode.generator;

public interface PasscodeGenerator {

    String generate(final int length);

}
