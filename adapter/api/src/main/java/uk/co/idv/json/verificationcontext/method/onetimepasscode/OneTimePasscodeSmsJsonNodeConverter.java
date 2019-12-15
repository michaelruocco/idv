package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.MobileNumber;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeSms;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeSmsEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeSmsIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;
import uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class OneTimePasscodeSmsJsonNodeConverter implements VerificationMethodJsonNodeConverter {

    @Override
    public boolean supportsMethod(final String name) {
        boolean supported = OneTimePasscodeSms.NAME.equals(name);
        log.info("returning supported {} for method name {}", supported, name);
        return supported;
    }

    @Override
    public VerificationMethod toMethod(final JsonNode node,
                                       final JsonParser parser,
                                       final DeserializationContext context) {
        try {
            final boolean eligible = node.get("eligible").asBoolean();
            if (eligible) {
                final PasscodeSettings settings = node.get("passcodeSettings").traverse(parser.getCodec()).readValueAs(PasscodeSettings.class);
                final Collection<MobileNumber> mobileNumbers = Arrays.asList(node.get("mobileNumbers").traverse(parser.getCodec()).readValueAs(MobileNumber[].class));
                return new OneTimePasscodeSmsEligible(settings, mobileNumbers);
            }
            return new OneTimePasscodeSmsIneligible();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
