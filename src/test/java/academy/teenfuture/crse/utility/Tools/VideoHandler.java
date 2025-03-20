package academy.teenfuture.crse.utility.Tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import com.microsoft.playwright.*;

public class VideoHandler {
    public static void generateVideo(Page page, Consumer<Page> logic, String testName) throws InterruptedException, IOException {
      System.setProperty("java.awt.headless", "false");

      logic.accept(page);
      Thread.sleep(1000);

      // Define the path to the project directory and create a timestamped file name for the video
      String pathProject = System.getProperty("user.dir");
      LocalDateTime currentDateTime = LocalDateTime.now();
      String dateTimeSString = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
      
      String newVideoFileName = testName + "Video_" + dateTimeSString + ".webm";
      String videoDirPath = pathProject + File.separator + "video";

      // if no video foler, just create it
      File videoDir = new File(videoDirPath);
      if (!videoDir.exists()) {
        videoDir.mkdirs();
      }

      //delete if there is old videos once start new a test
      File[] oldVideos = videoDir.listFiles((dir, name) -> name.matches(testName + "Video_.*\\.webm"));
      if (oldVideos != null) {
        for (File oldVideo : oldVideos) {
          oldVideo.delete();
        }
      }

      Path recordedVideoPath = page.video().path();
      File recordedFile = recordedVideoPath.toFile();

      File destFile = new File(videoDirPath + File.separator + newVideoFileName);

      page.context().close();

      recordedFile.renameTo(destFile);
    }
}
