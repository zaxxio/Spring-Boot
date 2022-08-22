package com.avaand.app.controller;

import com.avaand.app.payload.Transaction;
import com.avaand.app.utility.EncryptionHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@RestController
public class TransactionController {

    private final List<Transaction> transactions = new ArrayList<>();

    @PostConstruct
    public void setup(){
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1.0);
        transactions.add(transaction);
    }

    @GetMapping("/getPublicKey")
    public Transaction getTransaction(@RequestParam("transactionId") Double transactionId) throws NoSuchAlgorithmException {
        EncryptionHelper encryptionHelper = new EncryptionHelper();
        KeyPair keyPair = encryptionHelper.generateRSAKeyPair();
        byte[] rsaPublicKey = keyPair.getPublic().getEncoded();
        byte[] rsaPrivateKey = keyPair.getPrivate().getEncoded();

        Transaction payload = transactions.stream()
                .filter((transaction -> Objects.equals(transaction.getTransactionId(), transactionId)))
                .findAny()
                .orElseThrow();

        payload.setRsaPublicKey(new String(Base64.getEncoder().encode(rsaPublicKey)));
        payload.setRsaPrivateKey(new String(Base64.getEncoder().encode(rsaPrivateKey)));

        return payload;
    }

}
