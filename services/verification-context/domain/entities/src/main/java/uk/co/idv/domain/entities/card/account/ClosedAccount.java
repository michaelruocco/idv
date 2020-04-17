package uk.co.idv.domain.entities.card.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import uk.co.idv.domain.entities.card.number.CardNumber;

import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class ClosedAccount implements Account {

    public static final String STATUS = "closed";

    private final Collection<CardNumber> cardNumbers;

    public ClosedAccount(final CardNumber... cardNumbers) {
        this(Arrays.asList(cardNumbers));
    }

    @Override
    public String getStatus() {
        return STATUS;
    }

}
