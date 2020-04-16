package uk.co.idv.json.phonenumber;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface PhoneNumberMixin {

    @JsonIgnore
    boolean isMobile();

}
