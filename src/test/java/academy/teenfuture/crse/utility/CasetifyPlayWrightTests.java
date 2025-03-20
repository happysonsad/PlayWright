package academy.teenfuture.crse.utility;
import academy.teenfuture.crse.utility.Tools.ScreenshotHandler;
import academy.teenfuture.crse.utility.Tools.TestReporter;
import academy.teenfuture.crse.utility.Tools.VideoHandler;

import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Browser.NewContextOptions;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CasetifyPlayWrightTests {
  static Playwright playwright;
  static BrowserType browserType;
  static Browser browser;
  static BrowserContext browserContext;
  static Page page;

    @BeforeAll
    public static void setupAll() {
      playwright = Playwright.create();
      browserType = playwright.chromium();
      browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));

    }

    // ensure the "page" is open after each test
    @BeforeEach
    public void setupTest() {
      browserContext = browser.newContext(
        new NewContextOptions()
            .setViewportSize(null)
            .setRecordVideoDir(Paths.get("video"))
            .setRecordVideoSize(1920, 1080));
      page = browserContext.newPage();
    }
    
    @AfterEach
    public void inBetweenTest() {
      if (browserContext != null) {
        try {
          browserContext.close();
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
    }

    // Apple Test
    //@Test
    public void loopAllApplePhoneAndCases() throws InterruptedException, IOException {

      ScreenshotHandler.clearOldScreenshots("Apple");
      
      TestReporter.init("AppleReport.txt");
      TestReporter.log("\n------Starting Apple test run------");

      VideoHandler.generateVideo(page, targetPage -> {
        targetPage.navigate("https://www.casetify.com/en_UK/");
      
        //dismiss the cookies consent banner by click the <body>
        //dismiss the promotion modal box by click an absolute position
        page.locator("body").click();
        page.mouse().click(10, 10);
      
        //click nav bar menu
        page.locator("//label[contains(@class,'hamburger-button navbar-toggle')]").click();
        // click "Devices"
        page.locator("(//a[contains(@class,'nav-d-flex flex-direction-column')])[1]").click();
        // click "Customization"
        page.locator("(//a[@for='cb-49816'])[1]").click();
        //click "Phone Cases"
        page.locator("(//a[@class='item-cta light-border'])[1]").click();

        // will redirect to Customization Page "Device Type"

        // locate "next button" and click it in order to go to "Device Model"
        Locator nextButton = page.locator("(//div[@class='nav-next nav-btn'])[1]");
        nextButton.click();

        //click "Choose Your Phone"
        Locator phoneChoices = page.locator("(//div[@class='flex-grow-1 brand-desc brand-selector'])[1]");
        phoneChoices.click();

        // click "Apple" and walk through the list of Apple phones
        Locator applePhoneButton = page.locator("//li[normalize-space()='Apple']");
        applePhoneButton.click();
      
        //locate all phone model <li>
        Locator phoneModels = page.locator("ul.device-list.p-2 li");
        int phoneModelsCount = phoneModels.count();
      
        // for test report
        TestReporter.log("\nFound " + phoneModelsCount + " phone models");
      
        //loop through each phone model
        for (int p = 0; p < phoneModelsCount; p++) {
          Locator currentPhoneModel = phoneModels.nth(p);
          PlaywrightAssertions.assertThat(currentPhoneModel).isVisible();

          // for test report
          String phoneModelName = currentPhoneModel.innerText().trim();
          int currentPhoneModelNumber = p + 1;
          TestReporter.log("\n*Selecting phone model " + (currentPhoneModelNumber) + ": " + phoneModelName + "*");          
          
          currentPhoneModel.click();   

          DeviceColorLooper.loopAllDeviceColor(page, phoneModelName, nextButton);

          CaseModelLooper.loopAllPhoneCaseModel(page, phoneChoices, phoneModelName, nextButton);  
        }
      }, "Apple");
      TestReporter.log("\n------Apple test run completed------");
      TestReporter.close();
    }

    // Samsung Test
    @Test
    public void loopAllSamsungPhoneAndCases() throws InterruptedException, IOException {

      ScreenshotHandler.clearOldScreenshots("Samsung");

      TestReporter.init("SamsungReport.txt");
      TestReporter.log("\n------Starting Samsung test run------");

      VideoHandler.generateVideo(page, targetPage -> {
        targetPage.navigate("https://www.casetify.com/en_UK/");

        //dismiss the cookies consent banner by click the <body>
        //dismiss the promotion modal box by click an absolute position
        page.locator("body").click();
        page.mouse().click(10, 10);

        //click nav bar menu
        page.locator("//label[contains(@class,'hamburger-button navbar-toggle')]").click();
        //click "Devices"
        page.locator("(//a[contains(@class,'nav-d-flex flex-direction-column')])[1]").click();
        //click "Customization"
        page.locator("(//a[@for='cb-49816'])[1]").click();
        //click "Phone Cases"
        page.locator("(//a[@class='item-cta light-border'])[1]").click();

        // will redirect to Customization Page "Device Type"

        // locate "next button" and click it in order to go to "Device Model"
        Locator nextButton = page.locator("(//div[@class='nav-next nav-btn'])[1]");
        nextButton.click();

        //click "Choose Your Phone"
        Locator phoneChoices = page.locator("(//div[@class='flex-grow-1 brand-desc brand-selector'])[1]");
        phoneChoices.click();

        // click "Samsung" and walk through the list of Apple phones
        Locator samsungPhoneButton = page.locator("//li[normalize-space()='Samsung']");
        samsungPhoneButton.click();
      
        //locate all phone model <li>
        Locator phoneModels = page.locator("ul.device-list.p-2 li");
        int phoneModelsCount = phoneModels.count();
      
        // for test report
        TestReporter.log("\nFound " + phoneModelsCount + " phone models");
      
        //loop through each phone model
        for (int p = 0; p < phoneModelsCount; p++) {
          Locator currentPhoneModel = phoneModels.nth(p);
          PlaywrightAssertions.assertThat(currentPhoneModel).isVisible();

          // for test report
          String phoneModelName = currentPhoneModel.innerText().trim();
          int currentPhoneModelNumber = p + 1;
          TestReporter.log("\n*Selecting phone model " + (currentPhoneModelNumber) + ": " + phoneModelName + "*");          
          
          currentPhoneModel.click();
          try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

          DeviceColorLooper.loopAllDeviceColor(page, phoneModelName, nextButton);

          CaseModelLooper.loopAllPhoneCaseModel(page, phoneChoices, phoneModelName, nextButton);  
        }
      }, "Samsung");

      TestReporter.log("\n-------Samsung test run completed-------");
      TestReporter.close();
    }

    // Google test
    //@Test
    public void loopAllGooglePhoneAndCases() throws InterruptedException, IOException {

      ScreenshotHandler.clearOldScreenshots("Google");

      TestReporter.init("GoogleReport.txt");
      TestReporter.log("\n-------Starting Google test run-------");

      VideoHandler.generateVideo(page, targetPage -> {
        targetPage.navigate("https://www.casetify.com/en_UK/");

        //dismiss the cookies consent banner & the promotion modal box
        page.locator("body").click();
        page.mouse().click(10, 10);

        //click nav bar menu
        page.locator("//label[contains(@class,'hamburger-button navbar-toggle')]").click();
        //click "Devices"
        page.locator("(//a[contains(@class,'nav-d-flex flex-direction-column')])[1]").click();
        //click "Customization"
        page.locator("(//a[@for='cb-49816'])[1]").click();
        //click "Phone Cases"
        page.locator("(//a[@class='item-cta light-border'])[1]").click();

        // will redirect to Customization Page "Device Type"

        // locate "next button" and click it in order to go to "Device Model"
        Locator nextButton = page.locator("(//div[@class='nav-next nav-btn'])[1]");
        nextButton.click();

        //click "Choose Your Phone"
        Locator phoneChoices = page.locator("(//div[@class='flex-grow-1 brand-desc brand-selector'])[1]");
        phoneChoices.click();

        // click "Google" and walk through the list of Apple phones
        Locator googlePhoneButton = page.locator("//li[normalize-space()='Google']");
        googlePhoneButton.click();
      
        //locate all phone model <li>
        Locator phoneModels = page.locator("ul.device-list.p-2 li");
        int phoneModelsCount = phoneModels.count();
      
        // for test report
        TestReporter.log("\nFound " + phoneModelsCount + " phone models");
      
        //loop through each phone model
        for (int p = 0; p < phoneModelsCount; p++) {
          Locator currentPhoneModel = phoneModels.nth(p);
          PlaywrightAssertions.assertThat(currentPhoneModel).isVisible();

          // for test report
          String phoneModelName = currentPhoneModel.innerText().trim();
          int currentPhoneModelNumber = p + 1;
          TestReporter.log("\n*Selecting phone model " + (currentPhoneModelNumber) + ": " + phoneModelName + "*");          
          
          currentPhoneModel.click();
          try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

          DeviceColorLooper.loopAllDeviceColor(page, phoneModelName, nextButton);

          CaseModelLooper.loopAllPhoneCaseModel(page, phoneChoices, phoneModelName, nextButton);  
        }
      }, "Google");
      
      TestReporter.log("\n-------Google test run completed-------");
      TestReporter.close();
    }

    @AfterAll
    public static void endOfTheTestHiYanSir() throws InterruptedException{
      playwright.close();
    }

}
