package uk.co.idv.json.activity.converter;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.activity.Login;


@Slf4j
public class LoginJsonNodeConverter extends SingleSimpleActivityJsonNodeConverter {

    public LoginJsonNodeConverter() {
        super(Login.NAME);
    }

}
