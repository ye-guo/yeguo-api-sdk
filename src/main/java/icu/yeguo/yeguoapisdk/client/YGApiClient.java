package icu.yeguo.yeguoapisdk.client;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import icu.yeguo.yeguoapisdk.exception.YGApiException;
import icu.yeguo.yeguoapisdk.utils.SignUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
public class YGApiClient {

    private String accessKey;
    private String secretKey;
    private String signature;
    private static final String GATEWAY = "http://localhost:8081";
    private static final String API_IP_ADDRESS = "/api/ip/ipaddress";
    private static final String API_WEATHER = "/api/weather";
    private static final String API_PHONE_LOCATION = "/api/common/teladress";
    private static final String API_SITE_ICP = "/api/site/icp";
    private static final String API_QRCODE_ENCODE = "/api/qrcode/encode";
    private static final String API_QRCODE_DECODE = "/api/qrcode/decode";
    private static final String API_ONE_FILM = "/api/common/OneFilm";
    private static final String API_OCR_RECOGNITION = "/api/ocr/recognition";
    private static final String API_SEARCH_ANIME_INFO = "/api/search/anilistInfo";
    private static final String API_TODAY_INFO = "/api/common/today";
    private static final String API_HOT_SEARCH = "/api/common/fetchHotSearchBoard";

    public YGApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.signature = SignUtil.generateSignature(accessKey + secretKey);
    }

    private String sendRequest(String url, Map<String, Object> paramMap, boolean isPost) {
        paramMap.put("accessKey", accessKey);
        paramMap.put("signature", signature);
        try {
            return isPost ? HttpUtil.post(url, paramMap) : HttpUtil.get(url, paramMap);
        } catch (HttpException e) {
            log.error("HTTP error during {} request to URL: {} with parameters: {}",
                    isPost ? "POST" : "GET", url, paramMap, e);
            throw new YGApiException("HTTP error during request", e);
        } catch (RuntimeException e) {
            log.error("Runtime error during {} request to URL: {} with parameters: {}",
                    isPost ? "POST" : "GET", url, paramMap, e);
            throw new YGApiException("Runtime error during request", e);
        } catch (Exception e) {
            log.error("Unexpected error during {} request to URL: {} with parameters: {}",
                    isPost ? "POST" : "GET", url, paramMap, e);
            throw new YGApiException("Unexpected error during request", e);
        }
    }

    private String sendRequest(String url, Map<String, Object> paramMap) {
        paramMap.put("accessKey", accessKey);
        paramMap.put("signature", signature);
        try {
            return HttpUtil.get(url, paramMap);
        } catch (HttpException e) {
            log.error("HTTP error during GET request to URL: {} with parameters: {}", url, paramMap, e);
            throw new YGApiException("HTTP error during GET request", e);
        } catch (Exception e) {
            log.error("Unexpected error during GET request to URL: {} with parameters: {}", url, paramMap, e);
            throw new YGApiException("Unexpected error during GET request", e);
        }
    }

    public String getIpAddress(String ip) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("ip", ip);
        return sendRequest(GATEWAY + API_IP_ADDRESS, paramMap);
    }

    // 2.获取天气
    public String getCityWeather(String cityName) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("city_name", cityName);
        return sendRequest(GATEWAY + API_WEATHER, paramMap);
    }

    // 3.获取手机归属地
    public String getPhoneLocation(String mobile) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("mobile", mobile);
        return sendRequest(GATEWAY + API_PHONE_LOCATION, paramMap);
    }

    // 4.获取网站备案信息
    public String getSiteIcp(String domain) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("domain", domain);
        return sendRequest(GATEWAY + API_SITE_ICP, paramMap);
    }

    // 5.二维码生成
    public String getQrcodeEncode(String text) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("text", text);
        return sendRequest(GATEWAY + API_QRCODE_ENCODE, paramMap);
    }

    public String getQrcodeEncode(String text, Integer m) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("text", text);
        paramMap.put("m", m);
        return sendRequest(GATEWAY + API_QRCODE_ENCODE, paramMap);
    }

    // 6.二维码解析
    public String getQrcodeDecode(String filePath) {
        return handleFile(filePath, API_QRCODE_DECODE);
    }

    // 7.每日电影
    public String getOneFilm() {
        HashMap<String, Object> paramMap = new HashMap<>();
        return sendRequest(GATEWAY + API_ONE_FILM, paramMap);
    }

    // 8.看图识物
    public String recognition(String filePath) {
        return handleFile(filePath, API_OCR_RECOGNITION);
    }

    // 9.以图识番
    public String searchAnimeInfo(String filePath) {
        return handleFile(filePath, API_SEARCH_ANIME_INFO);
    }

    // 10.日读世界60s
    public String getTodayInfo() {
        HashMap<String, Object> paramMap = new HashMap<>();
        return sendRequest(GATEWAY + API_TODAY_INFO, paramMap);
    }

    // 11.热搜榜
    public String getHotSearch(String type) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("type", type);
        return sendRequest(GATEWAY + API_HOT_SEARCH, paramMap);
    }

    private String handleFile(String filePath, String apiUri) {
        if (!FileUtil.exist(filePath)) {
            throw new IllegalArgumentException("File does not exist: " + filePath);
        }
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", FileUtil.file(filePath));
        return sendRequest(GATEWAY + apiUri, paramMap, true);
    }
}
