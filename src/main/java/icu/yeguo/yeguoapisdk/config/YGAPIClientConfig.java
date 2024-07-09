package icu.yeguo.yeguoapisdk.config;

import icu.yeguo.yeguoapisdk.client.YGAPIClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties(prefix = "yeguo.api")
@Data
public class YGAPIClientConfig {

    public String accessKey;
    public String secretKey;

    @Bean
    public YGAPIClient ygapiClient() {
        return new YGAPIClient(accessKey, secretKey);
    }
}
