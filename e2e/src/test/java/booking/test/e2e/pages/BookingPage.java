package booking.test.e2e.pages;

import com.microsoft.playwright.Page;

public class BookingPage {

    private Page page;

    public BookingPage(Page page) {
        this.page = page;
    }

   
    public void clickBooking() {
        page.locator("xpath=//a[@href='#booking']").click();
    }

    public void enterCheckinDate(String date) {
        page.locator("xpath=//label[contains(text(),'Check In')]//following-sibling::div//input").fill(date);
    }
    
    public void enterCheckOurDate(String date) {
        page.locator("xpath=//label[contains(text(),'Check Out')]//following-sibling::div//input").fill(date);
    }
    
    public boolean getBookingConfirmed() {
        return page.locator("xpath=//*[contains(text(),'Booking Confirmed')]").isVisible();
    }
    
    public boolean getRoomType(String roomType) {
        return page.locator("xpath=//h1[contains(text(),'"+roomType+"')]").isVisible();
    }
    
    public void enterFirstName(String name) {
        page.locator("xpath=//input[@placeholder='Firstname']").fill(name);
    }
    
    public void enterLastName(String lName) {
        page.locator("xpath=//input[@placeholder='Lastname']").fill(lName);
    }
    
    public void enterEmail(String email) {
        page.locator("xpath=//input[@placeholder='Email']").fill(email);
    }
    
    public void enterPhone(String phone) {
        page.locator("xpath=//input[@placeholder='Phone']").fill(phone);
    }
    
    public void clickCheckAvailability() {
        page.locator("xpath=//button[contains(text(),'Check Availability')]").click();
    }
    
    public void clickReserveNow() {
        page.locator("xpath=//button[contains(text(),'Reserve Now')]").click();
    }
   
    
    public void selectRoomType(String roomType) {
        page.locator("xpath=//h5[contains(text(),'"+roomType+"')]/ancestor::div[contains(@class,'card')]//a[contains(text(),'Book now')]").click();
    }
    
    public String getRoomInfo(String roomType) {
        return page.locator("xpath=//h5[contains(text(),'"+roomType+"')]/ancestor::div[contains(@class,'card-body')]").textContent();
    }
    
    public String getCheckInInfo() {
        return page.locator("xpath=//h3[contains(text(),'Check-in & Check-out')]/following-sibling::ul").textContent();
    }
    
    public String getAlert() {
        return page.locator("xpath=//div[@role='alert']").textContent();
    }
    
    public String getHouseRules() {
        return page.locator("xpath=//h3[contains(text(),'House Rules')]/following-sibling::ul").textContent();
    }
    
    public String getRoomRent() {
        return page.locator("xpath=//h3[contains(text(),'Price Summary')]/following-sibling::div[1]/span[2]").textContent().split("£")[1];
    }
  
    public String getCleaningFee() {
        return page.locator("xpath=//h3[contains(text(),'Price Summary')]/following-sibling::div[2]/span[2]").textContent().split("£")[1];
    }
    
    public String getServiceFee() {
        return page.locator("xpath=//h3[contains(text(),'Price Summary')]/following-sibling::div[3]/span[2]").textContent().split("£")[1];
    }
    
    public String getTotalFee() {
        return page.locator("xpath=//h3[contains(text(),'Price Summary')]/following-sibling::div[4]/span[2]").textContent().split("£")[1];
    }
    
    //h5[contains(text(),'Single')]/ancestor::div[contains(@class,'card')]
    
}
