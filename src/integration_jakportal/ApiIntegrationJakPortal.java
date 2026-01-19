package integration_jakportal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ApiIntegrationJakPortal {

    private static final Properties prop = new Properties();
    private SSLContext sslContext;
    private SSLSocketFactory sslFactory;
    private Scheme scheme;
    private HttpComponentsClientHttpRequestFactory factory;
    private String Key, pass, proxy_ip, proxy_port, body = "", token = "", clientid = "", clientkey = "", url = "", urlauth = "";
    private HttpHeaders head;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response, pendaftaran, detailperiksa;
    private SimpleClientHttpRequestFactory requestFactory;
    private HttpHeaders headers;
    private String username, key, requestJson;
    private byte[] encodedBytes;
    private int i = 1;

    public ApiIntegrationJakPortal() {
        try {
            url = koneksiDB.JAKPORTALURL();
            username = koneksiDB.JAKPORTALUSERNAME();
            key = koneksiDB.JAKPORTALKEY();
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
        }
    }

    public String Token() {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-username", username);
            headers.add("x-key", key);
            requestEntity = new HttpEntity(headers);
            root = mapper.readTree(getRest().exchange(url + "/auth", HttpMethod.GET, requestEntity, String.class).getBody());
            token = root.path("response").path("token").asText();
        } catch (Exception ex) {
            System.out.println("Notifikasi Token : " + ex);
        }
        return token;
    }

    public long GetUTCdatetimeAsString() {
        long millis = System.currentTimeMillis();

        return millis / 1000;
    }

    public RestTemplate getRest() throws NoSuchAlgorithmException, KeyManagementException {
        sslContext = SSLContext.getInstance("TLSv1.2");
        TrustManager[] trustManagers = {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }

                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }
            }
        };
        sslContext.init(null, trustManagers, new SecureRandom());
        sslFactory = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        scheme = new Scheme("https", 443, sslFactory);
        factory = new HttpComponentsClientHttpRequestFactory();
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        return new RestTemplate(factory);
    }

}
