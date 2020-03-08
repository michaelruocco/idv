package uk.co.idv.domain.usecases.verification.onetimepasscode.generator;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomPasscodeGenerator implements PasscodeGenerator {

    @Override
    public String generate(final int length) {
        return RandomStringUtils.randomNumeric(length);
    }

}
