package uk.co.idv.domain.entities.card.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.card.number.CardNumber;

import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class OpenAccount implements Account {

    public static final String STATUS = "open";

    private final Collection<CardNumber> cardNumbers;

    public OpenAccount(final CardNumber... cardNumbers) {
        this(Arrays.asList(cardNumbers));
    }

    @Override
    public String getStatus() {
        return STATUS;
    }

}
