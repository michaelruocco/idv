package uk.co.idv.domain.entities.phonenumber;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public abstract class AbstractPhoneNumber implements PhoneNumber {

    private final UUID id;
    private final String type;
    private final String value;

}
