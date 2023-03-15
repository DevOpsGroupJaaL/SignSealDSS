package io.swagger.certificates;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Random;

public class Certs {
    public void createCertificate(String userName, String userEmail, String certPass) throws IOException {
        String keyPemName = genRandomString();
        String certPemName = genRandomString();
        String pkCertName = genRandomString();
        String confName = genRandomString();
        createConfigFile(confName, userName, userEmail);
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("openssl req -x509 -newkey rsa:4096 -keyout "+ keyPemName +".pem -out "+ certPemName + ".pem -days 365 -nodes -config " + confName + ".cnf");
        System.out.println("1");
        String[] cmdArr = new String[6];
        cmdArr[0] = "openssl req -x509 -newkey rsa:4096 -keyout "+ keyPemName +".pem -out "+ certPemName + ".pem -days 365 -nodes\n";
        cmdArr[1] = "NL\n";
        cmdArr[2] = "North Holland\n";
        cmdArr[3] = "Amsterdam\n";
        cmdArr[4] = "SignSeal\n";
        cmdArr[5] = "IT\n";
        runtime.exec(cmdArr);
        System.out.println("2");

        Runtime runtime2 = Runtime.getRuntime();
//        String[] cmdArr2 = new String[3];
//        cmdArr2[0] = ";
//        cmdArr2[1] = certPass +"\n";
//        cmdArr2[2] = certPass +"\n";
        runtime2.exec("openssl pkcs12 -export -out "+pkCertName+".p12 -inkey "+ keyPemName +".pem -in "+ certPemName +".pem");
        //

        String str = "/bin/sh -c openssl req -x509 -newkey rsa:4096 -keyout "+ keyPemName +".pem -out "+ certPemName + ".pem -days 365 -nodes";
        Process p = runtime.exec("openssl req -x509 -newkey rsa:4096 -keyout "+ keyPemName +".pem -out "+ certPemName + ".pem -days 365 -nodes");

        System.out.println(p);
                runtime.exec("/bin/sh -c NL\n");
//        System.out.println("3");
//
//        runtime.exec("North Holland\n");
//        System.out.println("4");
//
//        runtime.exec("Amsterdam\n");
//        System.out.println("5");
//
//        runtime.exec("SignSeal\n");
//        System.out.println("6");
//
//        runtime.exec("IT\n");
//        System.out.println("7");
//
//        runtime.exec(userName+"\n");
//        System.out.println("8");
//
//        runtime.exec("\n");
//        System.out.println("9");

//        runtime.exec("openssl pkcs12 -export -out "+pkCertName+".p12 -inkey "+ keyPemName +".pem -in "+ certPemName +".pem");
//        System.out.println("10");
//
//        runtime.exec(certPass +"\n");
//        System.out.println("11");
//
//        runtime.exec(certPass +"\n");
//        System.out.println("12");


    }

    private String genRandomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

       return  random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private void createConfigFile(String fileName, String name, String email) {
        String conf = "[ req_distinguished_name ]\n" +
                "countryName                     = NL\n" +
                "stateOrProvinceName             = North Holland\n" +
                "localityName                    = Amsterdam\n" +
                "organizationName              = SignSeal\n" +
                "organizationalUnitName          = IT\n" +
                "commonName                      = "+ name +"\n" +
                "emailAddress                    = "+ email;

        try {
            FileWriter myWriter = new FileWriter(fileName + ".cnf");
            myWriter.write(conf);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
