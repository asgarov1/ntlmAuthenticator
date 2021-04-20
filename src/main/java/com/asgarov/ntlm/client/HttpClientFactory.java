package com.asgarov.ntlm.client;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClientFactory {

    public static CloseableHttpClient createClientWithNTLMAuthentication(String username, String password, String domain) {
        var credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new NTCredentials(username, password, "", domain));
        return HttpClients.custom()
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
    }

}
