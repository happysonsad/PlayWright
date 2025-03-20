package academy.teenfuture.crse.utility.Tools;
import com.microsoft.playwright.Page;

public class ScreenshotsCondition {
    public static void caputurnDeviceColorMoreThanFour(Page page,int deviceColorCount, String phoneModelName) {
        if (deviceColorCount > 4) {
            ScreenshotHandler.captureFullPageScreenshot(page, "moreThan_4_deviceColor", phoneModelName, "Google");
          } 
    }

    public static void caputureCaseModelLessThanThree(Page page, int caseModelItemsCount, String phoneModelName) {
        if (caseModelItemsCount < 3) {
            ScreenshotHandler.captureFullPageScreenshot(page, "lessThan_3_caseModel", phoneModelName, "Google");
          }
    }
}
