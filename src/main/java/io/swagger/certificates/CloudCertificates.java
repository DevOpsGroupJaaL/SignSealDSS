package io.swagger.certificates;

import io.swagger.aws.S3;
import software.amazon.awssdk.services.s3.S3Client;


public class CloudCertificates {
    private final String BUCKET_NAME = "sign-seal-certs";
    private final String SIGN_IMAGE = "seal.jpeg";
    public String saveCertificate(String certificateUser, String certificatePassword, String certificateName) throws Exception {
        S3 s3 = new S3();
        S3Client s3Client = s3.getS3Client();
        GeneratePKCS gen = new GeneratePKCS();
        byte[] certificateBytes = gen.createPKCS(certificateUser, certificateName, certificatePassword);
        S3.putS3Object(s3Client, BUCKET_NAME, certificateUser + ".p12", certificateBytes);

        return "OK";
    }

    public byte[] getCertificate(String certificateUser) {
        S3 s3 = new S3();
        S3Client s3Client = s3.getS3Client();

        return s3.getObjectBytes(s3Client, BUCKET_NAME, certificateUser + ".p12");
    }

    public byte[] getSigningImage(S3Client s3Client, S3 s3) {
        return s3.getObjectBytes(s3Client, BUCKET_NAME, SIGN_IMAGE);
    }
}
