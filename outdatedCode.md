    // Samsung Test
    @Test
    public void loopAllSamsungPhoneAndCases() throws InterruptedException, IOException {

      ScreenshotHandler.clearOldScreenshots("Samsung");

      TestReporter.init("SamsungReport.txt");
      TestReporter.log("------Starting Samsung test run------");

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
        
        //locate "next button" and click it
        Locator nextButton = page.locator("(//div[@class='nav-next nav-btn'])[1]");
        nextButton.click();
        
        //click "Choose Your Phone"
        Locator phoneChoices = page.locator("(//div[@class='flex-grow-1 brand-desc brand-selector'])[1]");
        phoneChoices.click();

        // choose Samsung
        Locator SamsungBtn = page.locator("//li[normalize-space()='Samsung']");
        SamsungBtn.click();

        //locate all phone model <li>
        Locator phoneModels = page.locator("ul.list-buttons.vertical li");
        int phoneModelsCount = phoneModels.count();

        // for test report
        TestReporter.log("\nFound " + phoneModelsCount + " phone models");

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
      }, "Samsung");

      TestReporter.log("\n-------Samsung test run completed-------");
      TestReporter.close();
    }