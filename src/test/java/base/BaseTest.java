package base;

import configs.ConfigManager;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

// BaseTest class to set up RestAssured configuration
public class BaseTest {

    @BeforeSuite
    public void setUpEnvironment() {
        String env = System.getProperty("env", "dev"); // default dev
//        String baseUrl = RestAssured.baseURI = ConfigManager.get("baseUrl");

        // Tạo file environment.properties cho Allure
        Properties props = new Properties();
        props.setProperty("Environment", env);
//        props.setProperty("Base URL", baseUrl);
        props.setProperty("Tester","Hoàng Tỉnh");

        try {
            File file = new File("target/allure-results/environment.properties");
            file.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            props.store(fos, "Allure Environment Properties");
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create environment.properties for Allure", e);
        }
    }

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = ConfigManager.get("baseUrl");
//        RestAssured.basePath = ConfigManager.get("basePath");
    }
}
