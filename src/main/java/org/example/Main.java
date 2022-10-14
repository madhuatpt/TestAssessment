package org.example;

import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "C:\\Madhu\\Automation\\Selenium\\Dependencies\\BrowserDriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        /*1. Launch spaceX website */
        driver.get("https://shop.spacex.com/");
        driver.manage().window().maximize();
        Thread.sleep(3000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        driver.findElement(By.xpath("//*[@id=\"section-header\"]/div/div[4]/nav/ul/li[2]/button")).click();
        Thread.sleep(3000);

        /*2. Search for SPACE */
        driver.findElement(By.xpath("//*[@id=\"search-input\"]")).sendKeys("space");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"Search\"]/div/div[2]/div/div[1]/a")));
        driver.findElement(By.xpath("//*[@id=\"Search\"]/div/div[2]/div/div[1]/a")).click();
        Thread.sleep(3000);

        /* Get list of items returned from search and print search results */
        List<WebElement> cntOfResultElements = driver.findElements(By.className("Grid__Cell"));
        int getDesiredResultIndex=0;
        int cntItems=0;
        for (WebElement cntOfResultElement : cntOfResultElements)
        {
            cntItems=cntItems + 1;
            System.out.println(cntOfResultElement.findElement(By.className("ProductItem__Wrapper")).getText().replace("\n", " "));
            String productName = cntOfResultElement.findElement(By.className("ProductItem__Wrapper")).getText().split("\n")[0];
            if(productName.equals("MEN'S SPACEX POLO"))
                getDesiredResultIndex = cntItems;
        }

        /* 3. Click on MEN'S SPACEX POLO */
        driver.findElement(By.xpath("//*[@id=\"main\"]/section/ul/li["+getDesiredResultIndex+"]/div/div/a/div/img[2]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"shopify-section-product-template\"]/section/div[1]/div[2]/div[1]/div/div[1]/h1")));

        /*Validate the presence of Product Price and Details*/
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"shopify-section-product-template\"]/section/div[1]/div[2]/div[1]/div/div[1]/div[1]/span/span")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"shopify-section-product-template\"]/section/div[1]/div[2]/div[1]/div/div[1]/div[2]/div")).isDisplayed());

        /*Change the quantity and click add to cart*/
        driver.findElement(By.xpath("//*[@id=\"product_form_6537598271567\"]/div[1]/div[4]/div/span[2]")).click();
        Thread.sleep(10000);
        driver.findElement(By.className("ProductForm__AddToCart")).click();

        /* 4. Loop to verify if the actual price and expected price matches when there is change in quantity */
        for (int i=0; i < 3 ;i++)
        {
            driver.findElement(By.xpath("//*[@id=\"shopify-section-cart-template\"]/section/div/div/form/table/tbody/tr/td[2]/div/div/div/a[2]")).click();
            Thread.sleep(5000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("Cart__Total")));

            int iPrice = Integer.parseInt(driver.findElement(By.className("CartItem__Price")).getText().replace("$", "").replace(".00", ""));
            int iQuantity = Integer.parseInt(driver.findElement(By.className("QuantitySelector__CurrentQuantity")).getAttribute("value"));
            String expectedTotalPrice = String.valueOf(iPrice * iQuantity);
            System.out.println("Actual Price/unit: " + iPrice + " | Increasing Quantity to: " + iQuantity + " | Actual Total Price: " + driver.findElement(By.className("Cart__Total")).getText().replace("TOTAL:\n", "").replace("$", "").replace(".00", ""));
            Assert.assertEquals(expectedTotalPrice, driver.findElement(By.className("Cart__Total")).getText().replace("TOTAL:\n", "").replace("$", "").replace(".00", ""));
        }

        Thread.sleep(10000);
        driver.quit();
    }
}

