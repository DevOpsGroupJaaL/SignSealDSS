package io.swagger.certificates;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class CreatePKCS12FileWithPassword {

    public void createPKCS() throws Exception {
        String password = "myPassword";
        String alias = "myAlias";
        String fileName = "myPKCSFile.p12";

        // Generate a self-signed certificate
        X509Certificate cert = generateCertificate("CN=myName", password);

        // Generate a key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();

        // Create a new KeyStore and add the key and certificate to it
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(null, null);
        ks.setKeyEntry(alias, keyPair.getPrivate(), password.toCharArray(), new Certificate[] { cert });

        // Save the KeyStore to a file with password protection
        FileOutputStream fos = new FileOutputStream(fileName);
        ks.store(fos, password.toCharArray());
        fos.close();

        System.out.println("PKCS12 file created successfully!");
    }

    private static X509Certificate generateCertificate(String dn, String password) throws Exception {
        // Generate a self-signed certificate using Bouncy Castle library
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(2048);
        KeyPair keyPair = kpGen.generateKeyPair();

        X500Principal principal = new X500Principal(dn);
//        X500Name issuer = new X500Name(dn);
        BigInteger serial = BigInteger.valueOf(new Date().getTime());

        JcaX509v3CertificateBuilder builder = new JcaX509v3CertificateBuilder(principal, serial, new Date(), new Date(System.currentTimeMillis() + 86400000L * 365),
                principal, keyPair.getPublic());
        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption").build(keyPair.getPrivate());
        X509CertificateHolder certHolder = builder.build(signer);
        JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
        certConverter.setProvider(new BouncyCastleProvider());
        return certConverter.getCertificate(certHolder);
    }
}
