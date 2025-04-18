package test.java.com.test;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;

public class WebAppTest {
    WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "C:/msedgedriver.exe");
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get("http://211.87.232.162:8080/toLogin");
        driver.findElement(By.name("username")).sendKeys("test");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.cssSelector(".btn.btn-lg.btn-primary.btn-block.mt-3")).click();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testLoginWithValidCredentials() {
        driver.get("http://211.87.232.162:8080/toLogin");
        driver.findElement(By.name("username")).sendKeys("test");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.cssSelector(".btn.btn-lg.btn-primary.btn-block.mt-3")).click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("/index"));
    }

    @Test
    public void testLoginWithEmptyUsername() {
        driver.get("http://211.87.232.162:8080/toLogin");
        driver.findElement(By.name("username")).sendKeys("");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.cssSelector(".btn.btn-lg.btn-primary.btn-block.mt-3")).click();
        Assertions.assertTrue(driver.getPageSource().contains("请填写此字段"));
    }

    @Test
    public void testRegisterWithExistingUsername() {
        driver.get("http://211.87.232.162:8080/register");
        driver.findElement(By.name("username")).sendKeys("test");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.name("confirmPassword")).sendKeys("123456");
        driver.findElement(By.cssSelector(".btn.btn-primary.btn-block.mt-3")).click();
        Assertions.assertTrue(driver.getPageSource().contains("用户名已存在"));
    }

    @Test
    public void testRegisterWithoutInvitationCode() {
        driver.get("http://211.87.232.162:8080/toRegister");
        driver.findElement(By.name("username")).sendKeys("newuser");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.name("confirmPassword")).sendKeys("123456");
        driver.findElement(By.cssSelector(".btn.btn-primary.btn-block.mt-3")).click();
        Assertions.assertTrue(driver.getPageSource().contains("邀请码不能为空"));
    }

    @Test
    public void testUpdateAvatarWithInvalidFile() {
        driver.get("http://211.87.232.162:8080/user/update-avatar/a835d3d204f74cf8a7449cc490123ea0");
        WebElement upload = driver.findElement(By.name("avatar"));
        upload.sendKeys("C:/Users/guogy/28452128311-1-16.mp4");
        driver.findElement(By.id("uploadButton")).click();
        Assertions.assertTrue(driver.getPageSource().contains("文件格式不支持"));
    }

    @Test
    public void testUpdateProfile() {
        driver.get("http://211.87.232.162:8080/userinfo/setting/a835d3d204f74cf8a7449cc490123ea0");
        driver.findElement(By.id("nickname")).clear();
        driver.findElement(By.id("nickname")).sendKeys("测试用户");
        driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg.btn-block")).click();
        Assertions.assertTrue(driver.getPageSource().contains("保存成功"));
    }

    @Test
    public void testPostQuestionWithEmptyFields() {
        driver.get("http://211.87.232.162:8080/question/write");
        driver.findElement(By.name("title")).sendKeys("测试问题");
        driver.findElement(By.id("blog-content")).sendKeys("123123");
        driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg.btn-block")).click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("/question"));
    }

    @Test
    public void testSaveDraftArticle() {
        driver.get("http://211.87.232.162:8080/blog/write");
        driver.findElement(By.name("title")).sendKeys("测试文章");
        driver.findElement(By.id("blog-content")).sendKeys("123123");
        driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg.btn-block")).click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("/blog"));
    }

    @Test
    public void testPostEmptyComment() {
        driver.get("http://211.87.232.162:8080/question/read/2df23003f19345338f0c28792c7d5651");
        driver.findElement(By.cssSelector(".btn.btn-primary.float-right")).click();
        Assertions.assertTrue(driver.getPageSource().contains("请填写此字段"));
    }
}