package booking.test.e2e.businessLibrary;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.AriaRole;

import booking.test.e2e.pages.BookingPage;

public class BookingHelper extends PlaywrightBase {

    protected BookingPage bookingPage;

    @Override
    @BeforeAll
    void launchBrowser() {
        super.launchBrowser();
    }

    @Override
    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
        context.route("**/*", route -> route.resume());
        page = context.newPage();
        bookingPage = new BookingPage(page);
        page.navigate("https://automationintesting.online/");
    }


    //Method to create booking based on room type and check in details like duration of stay and check in date with proper assertions
	 public void createBooking(String roomType, int checkInAfterDays, int stayDurationDays) {
	     // Calculate dynamic dates
	     LocalDate checkIn = LocalDate.now().plusDays(checkInAfterDays);
	     LocalDate checkOut = checkIn.plusDays(stayDurationDays);
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	     String checkInDate = checkIn.format(formatter);
	     String checkOutDate = checkOut.format(formatter);

	     //Generate random test data
	     String randomId = UUID.randomUUID().toString().substring(0, 5);
	     String firstName = "Test" + randomId;
	     String lastName = "User" + randomId;
	     String email = "test" + randomId + "@test.com";
	     String phone = String.valueOf(900000000000L + new Random().nextInt(999999999));

	     //Booking flow
	     bookingPage.clickBooking();
	     bookingPage.enterCheckinDate(checkInDate);
	     bookingPage.enterCheckOurDate(checkOutDate);
	     bookingPage.clickCheckAvailability();
	     LOGGER.info("Room Info: " + bookingPage.getRoomInfo(roomType));
	     bookingPage.selectRoomType(roomType);
	     LOGGER.info("Check-in Info: " + bookingPage.getCheckInInfo());
	     bookingPage.clickReserveNow();
	     bookingPage.clickReserveNow();
	     if (bookingPage.getAlert() != null) {
	         bookingPage.getAlert();
	     }

	     // Guest details
	     bookingPage.enterFirstName(firstName);
	     bookingPage.enterLastName(lastName);
	     bookingPage.enterEmail(email);
	     bookingPage.enterPhone(phone);

	     // Fee validation logs
	     LOGGER.info("Room Rent: " + bookingPage.getRoomRent());
	     LOGGER.info("Cleaning Fee: " + bookingPage.getCleaningFee());
	     LOGGER.info("Service Fee: " + bookingPage.getServiceFee());
	     LOGGER.info("Total Fee: " + bookingPage.getTotalFee());

	     bookingPage.clickReserveNow();
	     //these can be rewritten to make more dynamic
	     delay(5);
	     assertTrue(bookingPage.getBookingConfirmed());
	     assertTrue(bookingPage.getRoomType(roomType));
	     LOGGER.info("Booking Duration [" +getExpectedDateRange(checkInAfterDays,stayDurationDays)+"]");
	     String dates = page.locator("xpath=//p[contains(text(),'Your booking has been confirmed for the following dates:')]/following-sibling::p").textContent();
	     assertTrue(dates.equalsIgnoreCase(getExpectedDateRange(checkInAfterDays,stayDurationDays)));
	     if (page.locator("xpath=//html[contains(text(),'Application error: a client-')]").isVisible())
			LOGGER.info("Application errored out");
	     delay(5);
	 }
	 
	 // Method to get expected date range to assert in the format captured after booking
	 public String getExpectedDateRange(int checkInAfterDays, int stayDurationDays) {
	    LocalDate checkIn = LocalDate.now().plusDays(checkInAfterDays);
	    LocalDate checkOut = checkIn.plusDays(stayDurationDays);
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    return checkIn.format(formatter) + " - " + checkOut.format(formatter);
	}
}
