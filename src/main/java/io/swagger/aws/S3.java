package io.swagger.aws;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import java.util.List;
public class S3 {
    public S3Client getS3Client() {
        S3Client client = S3Client.builder()
                .region(Region.EU_CENTRAL_1)
                .endpointOverride(URI.create("https://s3.eu-central-1.amazonaws.com"))
                .forcePathStyle(true)
                .build();
        return client;
    }

    public byte[] getObjectBytes (S3Client s3, String bucketName, String keyName) {
        try {
            GetObjectRequest objectRequest = GetObjectRequest
                    .builder()
                    .key(keyName)
                    .bucket(bucketName)
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(objectRequest);
            return objectBytes.asByteArray();
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }

        return null;
    }

    public static String putS3Object(S3Client s3, String bucketName, String objectKey, byte[] objectByteArr) {

        try {
            Map<String, String> metadata = new HashMap<>();
//            metadata.put("x-amz-meta-myVal", "test");
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .metadata(metadata)
                    .build();

            PutObjectResponse response = s3.putObject(putOb, RequestBody.fromBytes(objectByteArr));
            return response.eTag();

        } catch (S3Exception e) {
            System.err.println(e.getMessage());
        }

        return "";
    }

    public List<Bucket> listBuckets(S3Client s3) {
        List<Bucket> buckets = s3.listBuckets().buckets();
        System.out.println("Your {S3} buckets are:");
        for (Bucket b : buckets) {
            System.out.println("* " + b.name());

        }

        return buckets;
    }

    public static void listBucketObjects(S3Client s3, String bucketName ) {

        try {
            ListObjectsRequest listObjects = ListObjectsRequest
                    .builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsResponse res = s3.listObjects(listObjects);
            List<S3Object> objects = res.contents();
            for (S3Object myValue : objects) {
                System.out.println("The name of the key is:" + myValue.key());
                System.out.println(myValue.key().getClass().getName());
                System.out.println("The object is " + calKb(myValue.size()) + " KBs");
                System.out.println("The owner is " + myValue.owner());
            }

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }

    //convert bytes to kbs.
    private static long calKb(Long val) {
        return val/1024;
    }
}
