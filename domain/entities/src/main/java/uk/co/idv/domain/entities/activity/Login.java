package uk.co.idv.domain.entities.activity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Login extends SimpleActivity {

    public static final String NAME = "login";

    public Login(final Instant timestamp) {
        super(NAME, timestamp);
    }

}
