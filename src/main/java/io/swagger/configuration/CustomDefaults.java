package io.swagger.configuration;

import software.amazon.awssdk.regions.Region;

public class CustomDefaults {
    public Region DefaultServerLocation = Region.EU_CENTRAL_1;
    public String s3Endpoint = "https://s3.eu-central-1.amazonaws.com";


    public Region getDefaultServerLocation() {
        return this.DefaultServerLocation;
    }

    public String getS3Endpoint() {
        return this.s3Endpoint;
    }
}
