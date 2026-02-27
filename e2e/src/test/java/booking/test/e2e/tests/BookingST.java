package booking.test.e2e.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import booking.test.e2e.businessLibrary.BookingHelper;

public class BookingST extends BookingHelper {

	@ParameterizedTest
    @ValueSource(strings = {"Single:2:3","Double:1:1", "Suite:4:2"})
    public void happyCase(String data) {
    	createBooking(data.split(":")[0], Integer.parseInt(data.split(":")[1]), Integer.parseInt(data.split(":")[2]));
    }   
}
