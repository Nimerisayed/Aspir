import static org.testng.Assert.fail;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

import org.testng.Assert;

//import dev.failsafe.internal.util.Assert;

@Test
public class MyClass {
	WebDriver driver = new ChromeDriver();
	String Url = "https://global.almosafer.com/ar";
	Random rand = new Random();

	@BeforeTest
	public void mySetup() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));

		driver.manage().window().maximize();
		int RandomURLINDEX = rand.nextInt(2);
		String[] myURLS = { "https://global.almosafer.com/ar", "https://global.almosafer.com/en" };

		driver.get(myURLS[RandomURLINDEX]);

		if (driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div/div/div")).isDisplayed()) {
			driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div/div/div/button[1]")).click();

		}

	}

	@Test(groups = "mid")
	public void CheckTheLanguage() {

		if (driver.getCurrentUrl().contains("ar")) {
			String ActualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");

			Assert.assertEquals(ActualLanguage, "ar");

		} else {
			String ActualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
			System.out.println(ActualLanguage);
			Assert.assertEquals(ActualLanguage, "en");

		}
	}

	@Test(groups = "low")

	public void checkTheCurrency() {

		WebElement CurrencyTab = driver
				.findElement(By.xpath("//*[@id=\"__next\"]/header/div/div[1]/div[2]/div/div[1]/div/button"));
		String ActualCurrency = CurrencyTab.getText();
		Assert.assertEquals(ActualCurrency, "SAR");
	}

	@Test(groups = "high")

	public void checkTheContactNumber() {
		WebElement ContactNumberTab = driver
				.findElement(By.xpath("//*[@id=\"__next\"]/header/div/div[1]/div[2]/div/a[2]/strong"));
		String ActualContactNumber = ContactNumberTab.getText();
		Assert.assertEquals(ActualContactNumber, "+966554400000");
	}

	@Test(enabled = false)
	public void HotelTab() throws InterruptedException {
		String[] ArabicCities = { "دبي", "جدة" };
		String[] EnglishCities = { "riyadh", "Jeddah", "dubai" };
		int randomEnglish = rand.nextInt(EnglishCities.length);

		int RandomArabic = rand.nextInt(ArabicCities.length);

		driver.findElement(By.id("uncontrolled-tab-example-tab-hotels")).click();
		WebElement hotelTabInput = driver.findElement(By.cssSelector("input:first-of-type"));

		WebElement MySelector = driver.findElement(By.cssSelector(".tln3e3-1.eFsRGb"));

		Select selector = new Select(MySelector);
		selector.selectByIndex(rand.nextInt(2));
		Thread.sleep(2000);

		if (driver.getCurrentUrl().contains("ar")) {
			hotelTabInput.sendKeys(ArabicCities[RandomArabic]);

			Thread.sleep(5000);
			WebElement AutoCompleteList = driver.findElement(By.cssSelector(".phbroq-4.UzzIN.AutoComplete__List"));

			List<WebElement> myList = AutoCompleteList.findElements(By.tagName("div"));
			myList.get(1).click();
			driver.findElement(By.cssSelector(
					".sc-jTzLTM.hQpNle.sc-1vkdpp9-6.iKBWgG.js-HotelSearchBox__SearchButton.btn.btn-primary.btn-block"))
					.click();

		} else {
			hotelTabInput.sendKeys(EnglishCities[randomEnglish]);
			Thread.sleep(5000);
			WebElement AutoCompleteList = driver.findElement(By.cssSelector(".phbroq-4.UzzIN.AutoComplete__List"));

			List<WebElement> myList = AutoCompleteList.findElements(By.tagName("div"));
			myList.get(1).click();
			driver.findElement(By.cssSelector(
					".sc-jTzLTM.hQpNle.sc-1vkdpp9-6.iKBWgG.js-HotelSearchBox__SearchButton.btn.btn-primary.btn-block"))
					.click();

		}
	}

	@Test(groups = "tow2")
	public void DateCheck() {
		LocalDate today = LocalDate.now();

		LocalDate expectedDepartureDate = today.plusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
		String expectedDepartureDateStr = expectedDepartureDate.format(formatter);
		String actualDepartureDateStr = "your_actual_departure_date_as_string";

		System.out.println(expectedDepartureDateStr);
		System.out.println(expectedDepartureDate);
		System.out.println();

		Assertion Assertion = new Assertion();
		Assertion.assertEquals(expectedDepartureDateStr, actualDepartureDateStr, "Departure date mismatch");

	}

	@Test(groups = "saven")
	public void checkingLoadPage() {

	}

	@Test(groups = "eight")
	public void myTest() throws InterruptedException {
		driver.get(
				"https://www.almosafer.com/en/hotels/Douai/13-11-2023/14-11-2023/2_adult?placeId=ChIJH3HiTzfJwkcR8G1kgT7xCgQ&city=Douai&sortBy=LOWEST_PRICE");

		Thread.sleep(8000);

		WebElement Container = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div[1]/div[2]"));

		List<WebElement> Prices = Container.findElements(By.className("Price__Value"));

		for (int i = 0; i < Prices.size(); i++) {

			String FirstPrice = Prices.get(0).getText();
			String LastPrice = Prices.get(Prices.size() - 1).getText();
			int FirstPriceAsNumber = Integer.parseInt(FirstPrice);

			int LastPriceAsNumber = Integer.parseInt(LastPrice);
			System.out.println(FirstPriceAsNumber);
			System.out.println(LastPriceAsNumber);
			
			Assert.assertEquals(FirstPriceAsNumber < LastPriceAsNumber, true);
		}
	}

	@AfterTest
	public void postTesting() {
	}
}
