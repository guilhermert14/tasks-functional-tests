package br.ce.wcaquino.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    public WebDriver acessarAplicacao() throws MalformedURLException {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
//        WebDriver driver = new ChromeDriver();
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.19:4444/wd/hub"), cap);
        driver.get("http://192.168.0.19:8001/tasks");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;

    }

    @Test
    public void deveSalvarTarefaComSucesso() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);
        }finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", message);
        }finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", message);
        }finally {
            driver.quit();
        }
    }
}
