package icu.yeguo.yeguoapisdk.config;

import icu.yeguo.yeguoapisdk.client.YGApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties(prefix = "yeguo.api")
@Data
public class YGApiClientConfig {

    public String accessKey;
    public String secretKey;
    public String gateway;

    @Bean
    public YGApiClient ygapiClient() {
        return new YGApiClient(accessKey, secretKey, gateway);
    }
}
