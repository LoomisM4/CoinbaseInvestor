package com.mw.coinbase.api;

import com.mw.coinbase.util.Constants;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.crypto.Mac;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author LoomisM4
 */
@Data
public class Request {
    private String apiKey;
    private String secretKey;
    private String passphrase;
    private String body;
    private String signature;

    private Constants constants = Constants.get();

    public <T> T send(String path, Method method, Function<CloseableHttpResponse, T> responseMapper) throws IOException, InterruptedException, NoSuchAlgorithmException {
        String timestamp = String.valueOf(Instant.now().toEpochMilli());
        timestamp = timestamp.substring(0, timestamp.length() - 3) + "." + timestamp.substring(timestamp.length() - 3);

        String prehash = timestamp + method.toString() + path + Optional.ofNullable(this.body).orElse("");
        this.sign(prehash);

        HttpUriRequest request;
        if (method == Method.GET)
            request = this.createHttpGetRequest(path, timestamp);
        else if (method == Method.POST)
            request = this.createHttpPostRequest(path, timestamp);
        else
            throw new IllegalStateException("Something is wrong with the request type");

        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(request);

        return responseMapper.apply(response);
    }

    private HttpGet createHttpGetRequest(String path, String timestamp) {
        HttpGet request = new HttpGet(URI.create(constants.getUrl() + path));
        request.setHeader("CB-ACCESS-KEY", this.apiKey);
        request.setHeader("CB-ACCESS-SIGN", this.signature);
        request.setHeader("CB-ACCESS-TIMESTAMP", timestamp);
        request.setHeader("CB-ACCESS-PASSPHRASE", this.passphrase);

        return request;
    }

    private HttpPost createHttpPostRequest(String path, String timestamp) {
        HttpPost request = new HttpPost(URI.create(constants.getUrl() + path));
        request.setHeader("CB-ACCESS-KEY", this.apiKey);
        request.setHeader("CB-ACCESS-SIGN", this.signature);
        request.setHeader("CB-ACCESS-TIMESTAMP", timestamp);
        request.setHeader("CB-ACCESS-PASSPHRASE", this.passphrase);
        request.setHeader("Content-Type", "application/json");

        try {
            request.setEntity(new StringEntity(this.body));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return request;
    }

    private void sign(String prehash) {
        Mac hmac = HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, Base64.decodeBase64(secretKey));
        byte[] data = hmac.doFinal(prehash.getBytes(StandardCharsets.UTF_8));
        this.signature = Base64.encodeBase64String(data);
    }
}
