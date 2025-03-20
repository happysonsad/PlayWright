package academy.teenfuture.crse.utility;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

import academy.teenfuture.crse.utility.Tools.ScreenshotsCondition;
import academy.teenfuture.crse.utility.Tools.TestReporter;

public class DeviceColorLooper {
    public static void loopAllDeviceColor(Page page, String phoneModelName, Locator nextButton) {
        // Locate Device Color <li> items
        Locator deviceColor = page.locator("ul.device-color-select div.device-color-list-item");
        int deviceColorCount = deviceColor.count();

        ScreenshotsCondition.caputurnDeviceColorMoreThanFour(page, deviceColorCount, phoneModelName);  
        
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
      
        // for test report
        TestReporter.log("Total " + deviceColorCount + " device colors option found");
        for (int l = 0; l < deviceColorCount; l++) {
          Locator currentDeviceColor = deviceColor.nth(l);
          PlaywrightAssertions.assertThat(currentDeviceColor).isVisible();
          
          // for test report
          String deviceColorName = currentDeviceColor.locator("div.color-box li").getAttribute("data-device-color-name");
          TestReporter.log("  " + phoneModelName + " - " + deviceColorName + " color");
          currentDeviceColor.click();
          
          try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        }
    }    
}
