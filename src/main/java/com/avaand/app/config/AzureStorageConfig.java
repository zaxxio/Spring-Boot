package com.avaand.app.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log
@Configuration
public class AzureStorageConfig {



    @Bean(value = "blobServiceClient")
    public BlobServiceClient blobClient(@Value("${azureURL}") String azureURL, @Value("${sasKey}") String sasKey){
        return new BlobServiceClientBuilder()
                .endpoint(azureURL)
                .sasToken(sasKey)
                .buildClient();
    }

}
