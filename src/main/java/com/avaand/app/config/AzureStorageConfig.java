package com.avaand.app.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Log
@Configuration
public class AzureStorageConfig {

    @Bean(value = "blobServiceClient")
    @Profile(value = "storage")
    public BlobServiceClient blobClient(@Value("${azureURL}") String azureURL, @Value("${sasKey}") String sasKey){
        return new BlobServiceClientBuilder()
                .endpoint(azureURL)
                .sasToken(sasKey)
                .buildClient();
    }

}
