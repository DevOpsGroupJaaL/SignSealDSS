package io.swagger.certificates;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;

public class GenCert {

    private static final String ALIAS = "myalias";
    private static final String PASSWORD = "mypassword";
    private static final String FILENAME = "mycertificate.p12";

    public void createPKCS() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        X509Certificate cert = generateCertificate(keyPair);

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(null, null);
        keyStore.setKeyEntry(ALIAS, keyPair.getPrivate(), PASSWORD.toCharArray(), new Certificate[]{cert});

        FileOutputStream fileOutputStream = new FileOutputStream(FILENAME);
        keyStore.store(fileOutputStream, PASSWORD.toCharArray());
        fileOutputStream.close();

        System.out.println("PKCS12 file generated successfully.");
    }

    private static X509Certificate generateCertificate(KeyPair keyPair) throws Exception {
        String dn = "CN=My Name, OU=My Organization, O=My Company, L=My City, ST=My State, C=My Country";
        long now = System.currentTimeMillis();
        Date startDate = new Date(now);
        Date endDate = new Date(now + 365 * 24 * 60 * 60 * 1000L);

        X509v3CertificateBuilder builder = new X509v3CertificateBuilder(
                new X500Name(dn),
                BigInteger.valueOf(now),
                startDate,
                endDate,
                new X500Name(dn),
                SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded())
        );

        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSA").build(keyPair.getPrivate());
        return new JcaX509CertificateConverter().getCertificate(builder.build(signer));
    }
}
