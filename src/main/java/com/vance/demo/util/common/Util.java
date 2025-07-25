package com.vance.demo.util.common;

import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.net.ssl.SSLContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import lombok.experimental.UtilityClass;

/**
 * 工具類別
 * 
 * @author Vance
 */
@UtilityClass
public class Util {

    /**
     * 當傳入值為null時，回傳一個不可異動的空物件
     *
     * @param <T>      the element type
     * @param iterable the iterable(List, Set)
     * @return
     * @since Apache commons-collections4 CollectionUtils
     */
    public static <T> Iterable<T> emptyIfNull(final Collection<T> collection) {
        return CollectionUtils.emptyIfNull(collection);
    }

    /**
     * 當傳入值為null時，回傳一個不可異動的空物件
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map the map, possibly <code>null</code>
     * @return an empty map if the argument is <code>null</code>
     * @since Apache commons-collections4 MapUtils
     */
    public static <K, V> Map<K, V> emptyIfNull(final Map<K, V> map) {
        return MapUtils.emptyIfNull(map);
    }

    /**
     * 是否符合freemarker exception string格式 (因為當傳入的字串為null會變成
     * freemarker.core.DefaultToExpression$EmptyStringAndSequenceAndHash)
     * 
     * @param value
     * @return
     */
    public static boolean isFreemarkerEmptyString(Object value) {
        return StringUtil.trim(value).toUpperCase().matches("^FREEMARKER\\.CORE\\.DEFAULTTOEXPRESSION.*$");
    }

    /**
     * 創建支持 TLSv1.2 的 RestTemplate，並可自定義信任策略
     * 
     * @return RestTemplate 實例
     * @throws Exception 如果 SSL 配置失敗
     */
    public static RestTemplate createRestTemplateWithTls12() throws Exception {
        // 警告：僅測試用，生產環境應傳入自定義信任策略或信任庫
        TrustStrategy trustStrategy = (X509Certificate[] chain, String authType) -> true;

        // 配置 TLSv1.2 協議並應用信任策略
        SSLContext sslContext = SSLContexts.custom()
                .setProtocol("TLSv1.2")
                .loadTrustMaterial(null, trustStrategy)
                .build();

        // 配置 TLS 策略並跳過主機名驗證
        DefaultClientTlsStrategy tlsStrategy = new DefaultClientTlsStrategy(sslContext,
                NoopHostnameVerifier.INSTANCE);

        // 配置連接管理器
        PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setTlsSocketStrategy(tlsStrategy)
                .build();

        // 創建 CloseableHttpClient
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        // 配置 RestTemplate 的請求工廠
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return createRestTemplate(requestFactory);
    }

    /**
     * 創建一個默認配置的 RestTemplate，支持 UTF-8 編碼
     * 
     * @return RestTemplate 實例
     */
    public static RestTemplate createRestTemplate() {
        return createRestTemplate(null);
    }

    /**
     * 創建一個自定義配置的 RestTemplate，支持 UTF-8 編碼
     * 
     * @param requestFactory 自定義的 HTTP 請求工廠，可為 null 使用默認配置
     * @return RestTemplate 實例
     */
    public static RestTemplate createRestTemplate(ClientHttpRequestFactory requestFactory) {
        RestTemplate restTemplate = new RestTemplate();
        if (requestFactory != null) {
            restTemplate.setRequestFactory(requestFactory);
        }
        // 配置 UTF-8 編碼的字符串轉換器，並確保響應頭包含字符集信息
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        // 在響應頭中添加 charset=UTF-8，避免客戶端誤解編碼
        stringConverter.setWriteAcceptCharset(true);
        for (int i = 0; i < restTemplate.getMessageConverters().size(); i++) {
            if (restTemplate.getMessageConverters().get(i) instanceof StringHttpMessageConverter) {
                restTemplate.getMessageConverters().set(i, stringConverter);
                break;
            }
        }
        // 用以下會沒效果，故使用上方的方式新增
        // restTemplate.setMessageConverters(Collections.singletonList(converter));
        return restTemplate;
    }

    /**
     * 安全地從映射中獲取指定鍵對應的 Map 值。
     * 
     * @param map 源映射
     * @param key 要檢索的鍵
     * @return 如果找到匹配的鍵且值為 Map 類型，則返回該 Map；
     *         否則使用 CastUtils 進行強制轉型（通常會返回 null 或拋出異常）
     */
    public static Map<String, Object> safeGetMap(Map<String, Object> map, String key) {
        return CastUtil.cast(safeGet(map, key, Map.class));
    }

    /**
     * 安全地從映射中獲取指定鍵對應的 List 值。
     * 
     * @param map 源映射
     * @param key 要檢索的鍵
     * @return 如果找到匹配的鍵且值為 List 類型，則返回該 List；
     *         否則使用 CastUtils 進行強制轉型（通常會返回 null 或拋出異常）
     */
    public static List<Map<String, Object>> safeGetList(Map<String, Object> map, String key) {
        return CastUtil.cast(safeGet(map, key, List.class));
    }

    /**
     * 安全地從映射中獲取指定鍵的值，並將其轉換為指定類型。
     * 
     * @param <T>  期望返回的值的類型
     * @param map  源映射
     * @param key  要檢索的鍵
     * @param type 期望返回值的類別
     * @return 如果找到匹配的鍵且類型正確，則返回對應的值；否則返回 null
     */
    public static <T> T safeGet(Map<String, Object> map, String key, Class<T> type) {
        return Optional.ofNullable(map.get(key)).filter(type::isInstance).map(type::cast).orElse(null);
    }
}
