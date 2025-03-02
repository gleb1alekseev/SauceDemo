package pages;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

@Log4j2
public class CartPage extends HeaderPage{
    private static final String PRODUCT_ITEM = "//*[text()='%s']/ancestor::*[@class=\"cart_item\"]";
    private static final String PRODUCT_PRICE = PRODUCT_ITEM + "//*[@class=\"inventory_item_price\"]";
    private static final String PRODUCT_QUANTITY = PRODUCT_ITEM + "//*[@class=\"cart_quantity\"]";
    private static final String REMOVE_BUTTON = PRODUCT_ITEM + "//button";
    private static final String CART_ITEM_CONTAINER = "//*[@class='cart_item']";

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage openCartPage(String url) {
        log.info("Open Cart Page URL {}", url);
        driver.get(url);
        return this;
    }

    public String getProductPrice(String productName) {
        String productPrice = driver.findElement(By.xpath(String.format(PRODUCT_PRICE, productName))).getText();
        log.info("Get price for product: {}. Price is: {}", productName, productPrice);
        return productPrice;
    }

    public int getProductQuantity() {
        int productQuantity = driver.findElements(By.xpath(CART_ITEM_CONTAINER)).size();
        log.info("Get product quantity: {}", productQuantity);
        return productQuantity;
    }

    public CartPage removeProductFromCart(String productName) {
        driver.findElement(By.xpath(String.format(REMOVE_BUTTON, productName))).click();
        log.info("Remove product '{}'");
        return null;
    }

    public boolean isProductDisplayed(String productName) {
        return !driver.findElements(By.xpath(String.format(PRODUCT_ITEM, productName))).isEmpty();
    }

    public void wait(By locator) {
        Wait<WebDriver> fluent = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        WebElement foo = fluent.until(driver -> driver.findElement(locator));
        foo.click();
    }
}
