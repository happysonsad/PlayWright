package academy.teenfuture.crse.utility;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

import academy.teenfuture.crse.utility.Tools.TestReporter;

public class PhoneCaseColorLooper {
    public static void loopAllPhoneCaseColor(Page page, String caseModelName, Locator nextButton, int caseModelItemsCount, String phoneModelName) {
      Locator caseColorItems = page.locator("div.variant-select ul.w-100.vp-desktop li");

      try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
      
      if (caseColorItems.count() > 1) {
        //if there is one more case color and "03" is "Case Color", then click [Next Button] to go to "Case Color"
        nextButton.click();
        
        int caseColorItemsCount = caseColorItems.count();
        
        // for test report
        TestReporter.log(" " + caseModelName + " has " + caseColorItemsCount + " color options");
      
        //loop all the case color              
        for (int j = 0; j < caseColorItemsCount; j++) {
          Locator currentCaseColor = caseColorItems.nth(j);
        
          PlaywrightAssertions.assertThat(currentCaseColor).isVisible();

          // for test report
          String caseColorName = currentCaseColor.locator("span.variant-desc.text-center").innerText();
          int currentCaseColorNumber = j + 1;
          TestReporter.log("  Case color " + (currentCaseColorNumber) + ": " + caseColorName);
          
          currentCaseColor.click();

          try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }


        }
        if (caseModelItemsCount > 1) {
          //click "Case Type" go back to case model
          Locator caseModelButton = page.locator("//div[contains(text(),'03. Case Type')]");
          caseModelButton.click();
        }

      }
      else {
        TestReporter.log(phoneModelName + " - " + caseModelName + " has only one color option");
      }
    }
}