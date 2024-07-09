package icu.yeguo.yeguoapisdk.client;

import cn.hutool.http.HttpUtil;
import icu.yeguo.yeguoapisdk.utils.SignUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class YGAPIClient {

    private String accessKey;
    private String secretKey;
    private String signature;


    public YGAPIClient() {
    }

    public YGAPIClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.signature = SignUtil.generateSignature(accessKey + secretKey);
    }

    private String sendRequest(String url, Map<String, Object> paramMap) {
        paramMap.put("accessKey", accessKey);
        paramMap.put("signature", signature);
        return HttpUtil.get(url, paramMap);
    }

    public String getIpAddress(String ip) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("ip", ip);
        return sendRequest("http://localhost:8082/api/ip/ipaddress", paramMap);
    }

    public String getCityWeather(String cityName) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("city_name", cityName);
        return sendRequest("http://localhost:8082/api/weather", paramMap);
    }

    public String getPhoneLocation(String mobile) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("mobile", mobile);
        return sendRequest("http://localhost:8082/api/common/teladress", paramMap);
    }

    public String getSiteIcp(String domain) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("domain", domain);
        return sendRequest("http://localhost:8082/api/site/icp", paramMap);
    }

}
