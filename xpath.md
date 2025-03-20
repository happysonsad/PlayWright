test command: 
./mvnw clean test

----

search button:
Locator searchButton = page.locator("//*[@id=\"casetify-menu\"]/nav/div[1]/div[2]/a");
searchButton.click();

search bar:
Locator searchBar = page.locator("//*[@id=\"vue-mobile-search\"]");
searchBar.fill("TinGor love GirlClass"); //enter keywords in search input
