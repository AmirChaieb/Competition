package com.Amir.Competition.utils;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Getter
@Component
public class RSAKeyProperties {
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public RSAKeyProperties(){
        KeyPair pair = keyGeneratorUtility.generateRsaKey();
        this.publicKey = (RSAPublicKey) pair.getPublic();
        this.privateKey = (RSAPrivateKey) pair.getPrivate();
    }

    public void setPublicKey(RSAPublicKey publicKey){
        this.publicKey = publicKey;
    }

    public void setPrivateKey(RSAPrivateKey privateKey){
        this.privateKey = privateKey;
    }
}
