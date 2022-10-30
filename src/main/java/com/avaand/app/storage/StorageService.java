package com.avaand.app.storage;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Log
@Service
public class StorageService {

    private final BlobServiceClient blobServiceClient;

    public StorageService(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
    }

    public void upload(){
        BlobContainerClient client = blobServiceClient.getBlobContainerClient("videos");
        BlobClient blobClient = client.getBlobClient("myblockblob.txt");
        String dataSample = "samples";
        blobClient.upload(BinaryData.fromString(dataSample), true);
        BinaryData binaryData = blobClient.downloadContent();
        log.info(binaryData.toString());
    }

    public void streamUpload(){
        BlobContainerClient client = blobServiceClient.getBlobContainerClient("videos");
        BlobClient blobClient = client.getBlobClient("password.txt");
        String sample = "sample";
        try(ByteArrayInputStream datastore = new ByteArrayInputStream(sample.getBytes())) {
            blobClient.upload(datastore, sample.length(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
