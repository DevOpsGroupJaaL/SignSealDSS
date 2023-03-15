package io.swagger.certificates;

import io.swagger.aws.S3;
import software.amazon.awssdk.services.s3.S3Client;


public class SaveCertificate {
    private String bucketName = "sign-seal-certs";
    public void saveCertificate(String certificateUser, String certificatePassword, String certificateName) throws Exception {
        S3 s3 = new S3();
        S3Client s3Client = s3.getS3Client();
        GenCert gen = new GenCert();
        gen.createPKCS();
//        s3.putS3Object(s3Client, bucketName, certificateUser, );
    }
}
