package academy.teenfuture.crse.utility;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

import academy.teenfuture.crse.utility.Tools.ScreenshotsCondition;
import academy.teenfuture.crse.utility.Tools.TestReporter;

public class CaseModelLooper {
    public static void loopAllPhoneCaseModel(Page page, Locator phoneChoices, String phoneModelName, Locator nextButton) {
      // Locate all <li> case model items
      Locator caseModelItems = page.locator("div.product-select ul.w-100.p-0.vp-desktop li");
      // Retrieve the number of case model items
      int caseModelItemsCount = caseModelItems.count();

      Locator caseColorItems = page.locator("div.variant-select ul.w-100.vp-desktop li");
      int caseColorItemsCount = caseColorItems.count();

      // capture screen if there are less than 3 case model options
      ScreenshotsCondition.caputureCaseModelLessThanThree(page, caseModelItemsCount, phoneModelName);

      // loop all case model if there are more than one case model options
      if (caseModelItemsCount > 1) {

        // from "Device Model" go to "Case Type"
        nextButton.click();

        TestReporter.log("Found " + caseModelItemsCount + " phone case models");

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        // Loop through each case model <li> element
        for (int i = 0; i < caseModelItemsCount; i++) {
          // Get the current product item using nth(index)
          Locator currentCaseModel = caseModelItems.nth(i);
        
          // Wait for the current item to be visible before interacting
          PlaywrightAssertions.assertThat(currentCaseModel).isVisible();
  
          // for test report, extract some case model product details
          String caseModelName = currentCaseModel.locator("span[class='text-center product-desc']").innerText();
          String price = currentCaseModel.locator("span[class='price text-center']").innerText();
          int currentCaseNumber = i + 1;
          TestReporter.log("Case " + currentCaseNumber + ": " + caseModelName + " - " + price);
  
          currentCaseModel.click();
  
          try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

          // loop all case color
          PhoneCaseColorLooper.loopAllPhoneCaseColor(page, caseModelName, nextButton, caseModelItemsCount, phoneModelName);

          // if loop all the phone case color, go back to the "Device Model" and choose a new phone model
          BackToDeviceModelHandler.clickBackToDeviceModel(page, caseModelItemsCount, phoneChoices, currentCaseNumber);
        }
      }

      // if there is only one case model option, but more than one case color: go to
      else if (caseModelItemsCount < 1 && caseColorItemsCount > 1) {
        String onlyOneCaseModelName = page.locator("//span[@class='product-desc']").innerText();
        TestReporter.log(phoneModelName + " has only one case model option: " + onlyOneCaseModelName);

        // loop all case color
        PhoneCaseColorLooper.loopAllPhoneCaseColor(page, onlyOneCaseModelName, nextButton, caseModelItemsCount, phoneModelName);

        // if loop all the phone case color, go back to the "Device Model" and choose a new phone model
        BackToDeviceModelHandler.withOnlyOneCaseModel(page, phoneChoices);

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
      }

      // if there is only one case model option and one case color option: back to "Device Model 
      else if (caseModelItemsCount < 1 && caseColorItemsCount < 1) {
        String onlyOneCaseAndColorName = page.locator("//span[@class='product-desc']").innerText();
        TestReporter.log(phoneModelName + " has only one case model & one case color option: " + onlyOneCaseAndColorName);
        BackToDeviceModelHandler.onlyOneCaseModelAndCaseColor(phoneChoices);
      }
    }
}
