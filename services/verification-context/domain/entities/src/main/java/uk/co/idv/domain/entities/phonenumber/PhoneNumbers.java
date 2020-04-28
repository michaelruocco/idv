package uk.co.idv.domain.entities.phonenumber;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class PhoneNumbers implements Iterable<PhoneNumber> {

    private final Collection<PhoneNumber> numbers;

    public PhoneNumbers(final PhoneNumber... numbers) {
        this(Arrays.asList(numbers));
    }

    @Override
    public Iterator<PhoneNumber> iterator() {
        return numbers.iterator();
    }

    public Stream<PhoneNumber> stream() {
        return numbers.stream();
    }

    public PhoneNumbers getMobileNumbers() {
        return new PhoneNumbers(numbers.stream()
                .filter(PhoneNumber::isMobile)
                .collect(Collectors.toList()));
    }

}
