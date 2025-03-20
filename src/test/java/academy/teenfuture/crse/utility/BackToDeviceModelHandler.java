package academy.teenfuture.crse.utility;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BackToDeviceModelHandler {
    public static void clickBackToDeviceModel(Page page, int caseModelItemsCount, Locator phoneChoices, int currentCaseNumber) {
        //click the "Done" button if in the last phone case items
        Locator deviceModelButton = page.locator("//div[contains(text(),'02. Device Model')]");
        if (caseModelItemsCount == (currentCaseNumber) ) {
          deviceModelButton.click();
        
          try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
          //click "Choose Your Phone" to choose any another phone model
          phoneChoices.click();
        }        
    }

    public static void withOnlyOneCaseModel(Page page, Locator phoneChoices) {
      //back to "Device Model"
      Locator deviceModelButton = page.locator("//div[contains(text(),'02. Device Model')]");
      deviceModelButton.click();
    
      try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
      //click "Choose Your Phone" to choose any another phone model
      phoneChoices.click();
  }

  public static void onlyOneCaseModelAndCaseColor(Locator phoneChoices) {
    //click "Choose Your Phone" to choose any another phone model
    phoneChoices.click();

    try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
  }
}
