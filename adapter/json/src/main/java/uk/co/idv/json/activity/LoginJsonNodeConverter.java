package uk.co.idv.json.activity;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.activity.Login;
import uk.co.idv.json.activity.simple.SingleSimpleActivityJsonNodeConverter;


@Slf4j
public class LoginJsonNodeConverter extends SingleSimpleActivityJsonNodeConverter {

    public LoginJsonNodeConverter() {
        super(Login.NAME);
    }

}
