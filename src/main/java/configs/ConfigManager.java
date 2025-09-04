package configs;

import java.util.Properties;

public class ConfigManager {
   private static final Properties properties = new Properties();
    private static final String ENV;
    static {
        // đọc ENV từ Maven/System property, mặc định = dev
        ENV=System.getProperty("env", "dev");
        String fileName = "config-" + ENV + ".properties";
        try {
            var input = ConfigManager.class.getClassLoader().getResourceAsStream(fileName);
            if (input == null) {
                throw new RuntimeException("Configuration file not found: " + fileName);
            }
            // Nạp file properties
            properties.load(input);;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration properties", e);
        }
    }

    // Lấy giá trị từ file properties
    public static String get(String key) {
        return properties.getProperty(key);
    }
    public static String getEnv() {
        return ENV;
    }
}
