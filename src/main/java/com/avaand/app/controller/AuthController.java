package com.avaand.app.controller;

import com.avaand.app.domain.Consumer;
import com.avaand.app.domain.Payload;
import com.avaand.app.domain.SecureWrapper;
import com.avaand.app.repository.ConsumerRepository;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

@Log
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {



}
