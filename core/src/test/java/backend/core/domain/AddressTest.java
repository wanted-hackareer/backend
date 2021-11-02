package backend.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressTest {

    @Test
    @DisplayName("Address 생성하기")
    public void createAddress() {
        //given
        Address address = Address.builder().city("서울시").district("강남구").street("강남로").build();

        //then
        assertThat(address.getCity()).isEqualTo("서울시");
        assertThat(address.getDistrict()).isEqualTo("강남구");
        assertThat(address.getStreet()).isEqualTo("강남로");
    }
}