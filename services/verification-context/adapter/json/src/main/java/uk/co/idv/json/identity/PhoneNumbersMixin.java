package uk.co.idv.json.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;

public interface PhoneNumbersMixin {

    @JsonIgnore
    PhoneNumbers getMobileNumbers();

}
