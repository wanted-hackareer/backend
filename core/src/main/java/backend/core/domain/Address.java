package backend.core.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String city;
    private String district;
    private String street;

    @Builder
    public Address(String city, String district, String street) {
        this.city = city;
        this.district = district;
        this.street = street;
    }
}
