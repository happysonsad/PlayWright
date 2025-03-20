package academy.teenfuture.crse.utility.Tools;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class ScreenshotHandler {
    public static void clearOldScreenshots(String testFolderLocation) {
        String folderPath = Paths.get("screenshots", testFolderLocation).toString();
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }
    
    public static void captureFullPageScreenshot(Page page, String fileNamePrefix, String phoneModel, String testFolderLocation) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String fileName = phoneModel + "_" + fileNamePrefix + "_" + timestamp + ".png";
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("screenshots", testFolderLocation, fileName))
                .setFullPage(true));
    }

    public static void captureElementScreenshot(Page page, String selector, String fileNamePrefix, String testFolderLocation) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String fileName = fileNamePrefix + "_element_" + timestamp + ".png";
        Locator element = page.locator(selector);
        element.screenshot(new Locator.ScreenshotOptions()
                .setPath(Paths.get("screenshots", testFolderLocation, fileName)));
    }

}




