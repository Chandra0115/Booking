
package booking.test.e2e.businessLibrary;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlaywrightBase {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String RUN_HEADLESS = "headlessUITests";
    Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    void launchBrowser() {
        playwright = Playwright.create();
        boolean headless = Boolean.parseBoolean(System.getProperty(RUN_HEADLESS));
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless)
                .setIgnoreDefaultArgs(Arrays.asList("ignoreHTTPSErrors:true")).setArgs(List.of("--lang=en-US")));
    }

    @AfterAll
    void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions().setLocale("en-US"));
        context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
        page = context.newPage();
    }

    @AfterEach
    void closeContext(TestInfo test) {
        context.tracing()
                .stop(new Tracing.StopOptions().setPath(Path.of("target", "playwright-trace",
                        String.format("%s-%s-%s.zip", test.getTestClass().map(Class::getSimpleName).orElse("null"),
                                test.getTestMethod().map(Method::getName).orElse("null"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss").format(LocalDateTime.now())))));
        context.close();
    }

    
    protected Page newPage() {
        return context.newPage();
    }


    protected void delay(int seconds) {
        try {
            Thread.sleep(Duration.ofSeconds(seconds).toMillis());
        } catch (InterruptedException e) {
            LOGGER.info("Exception", e);
        }
    }
}
