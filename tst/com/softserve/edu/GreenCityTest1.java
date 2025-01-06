package com.softserve.edu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GreenCityTest1 {
    private static final Long IMPLICITLY_WAIT_SECONDS = 10L;

    @Test
    public void checkNews() {
        System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        driver.manage().window().maximize();

        try {
            driver.get("https://www.greencity.cx.ua/");

            // Ожидание загрузки и клик по элементу
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement newsButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("app-header:nth-child(1) .nav-left-list:nth-child(4) > .url-name")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", newsButton);
            newsButton.click();

            // Проверка языка и переключение
            String lang = driver.findElement(By.cssSelector("ul[aria-label='language switcher'] span")).getText();
            if (lang.toLowerCase().equals("ua")) {
                driver.findElement(By.cssSelector(".header_arrow")).click();
                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ng-star-inserted > span"))).click();
            }

            // Переход на вкладку
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".nav-left-list:nth-child(1) > .url-name"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".tag-button:nth-child(3) .text"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".gallery-view-li-active:nth-child(2) .added-data p"))).click();

            // Проверка текста
            String actualText = driver.findElement(By.cssSelector(".ql-editor > p")).getText();
            Assertions.assertEquals("Explore the cutting edge of sustainability, climate action, and eco-innovation with Green Horizons. Discover inspiring stories, groundbreaking research, and simple actions that can help reshape our world into a greener, more resilient place for future generations", actualText);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}

