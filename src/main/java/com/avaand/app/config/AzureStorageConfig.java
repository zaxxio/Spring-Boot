package com.avaand.app.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureStorageConfig {

    @Bean(value = "blobServiceClient")
    public BlobServiceClient blobClient(){
        return new BlobServiceClientBuilder()
                .endpoint()
                .sasToken()
                .buildClient();
    }

}
