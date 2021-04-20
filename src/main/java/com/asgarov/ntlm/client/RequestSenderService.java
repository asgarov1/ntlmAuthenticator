package com.asgarov.ntlm.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class RequestSenderService {

    @Value("${ntlm.username}")
    private String username;

    @Value("${ntlm.password}")
    private String password;

    @Value("${ntlm.domain}")
    private String domain;

    public <T> ResponseEntity<T> getRequest(String url, Class<T> clazz) {
        try (var client = HttpClientFactory.createClientWithNTLMAuthentication(username, password, domain)) {
            var requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(client);
            return new RestTemplate(requestFactory).getForEntity(url, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("getForEntity threw an exception!");
        }
    }

    public <T> ResponseEntity<String> postRequest(T dto, String url) {
        try (var client = HttpClientFactory.createClientWithNTLMAuthentication(username, password, domain)) {
            var requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(client);
            return new RestTemplate(requestFactory).postForEntity(url, new HttpEntity<>(dto), String.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("postingForEntity threw an exception!");
        }
    }
}
