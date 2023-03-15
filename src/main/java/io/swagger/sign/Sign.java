package io.swagger.sign;

 import eu.europa.esig.dss.enumerations.SignerTextPosition;
 import eu.europa.esig.dss.enumerations.TextWrapping;
 import eu.europa.esig.dss.model.InMemoryDocument;
 import eu.europa.esig.dss.pades.*;
 import eu.europa.esig.dss.enumerations.SignatureLevel;
 import eu.europa.esig.dss.enumerations.DigestAlgorithm;
 import eu.europa.esig.dss.pdf.pdfbox.PdfBoxNativeObjectFactory;
 import eu.europa.esig.dss.utils.Utils;
 import eu.europa.esig.dss.validation.CommonCertificateVerifier;
 import eu.europa.esig.dss.pades.signature.PAdESService;
 import eu.europa.esig.dss.model.ToBeSigned;
 import eu.europa.esig.dss.model.SignatureValue;
 import eu.europa.esig.dss.model.DSSDocument;
 import java.nio.file.Files;
 import java.awt.*;
 import java.io.IOException;
 import java.nio.file.Paths;
 import java.security.KeyStore.PasswordProtection;

 import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
 import eu.europa.esig.dss.token.Pkcs12SignatureToken;

 import io.swagger.aws.S3;
 import io.swagger.certificates.Certs;
 import software.amazon.awssdk.services.s3.S3Client;

 import static eu.europa.esig.dss.utils.Utils.toByteArray;


public class Sign {
    private final String bucketName = "jaal-dsdss-documents";

    private byte[] convertDocToByteArr(String filePath) throws IOException {
      return Files.readAllBytes(Paths.get(filePath));
    }

    private SignatureImageParameters setImageParams() throws IOException {
     // Initialize visual signature and configure
     SignatureImageParameters imageParameters = new SignatureImageParameters();
     // set an image
     imageParameters.setImage(new InMemoryDocument(convertDocToByteArr("/home/aledmin/Dev/JaalSigning/target/seal.jpeg")));

     SignatureImageTextParameters textParameters = new SignatureImageTextParameters();

     // Allows you to set a DSSFont object that defines the text style (see more information in the section "Fonts usage")
     DSSFont font = new DSSJavaFont(Font.SERIF);
     textParameters.setFont(font);

     // Defines the text content
     textParameters.setText("I have written this signature!!");

     // Defines the color of the characters
     textParameters.setTextColor(Color.BLACK);
     textParameters.setSignerTextPosition(SignerTextPosition.BOTTOM);
     textParameters.setTextWrapping(TextWrapping.FONT_BASED);
     imageParameters.setTextParameters(textParameters);
     // initialize signature field parameters
     SignatureFieldParameters fieldParameters = new SignatureFieldParameters();
     imageParameters.setFieldParameters(fieldParameters);
     // the origin is the left and top corner of the page

     fieldParameters.setOriginY(30);
     fieldParameters.setOriginX(480);
     fieldParameters.setWidth(90);
     fieldParameters.setHeight(90);

     return imageParameters;
    }
    public String signDoc(String filePath, String certPath, String pass) throws IOException {
     // Test cert creation
    Certs cert = new Certs();
    cert.createCertificate("Ale", "testing123", "pass");

    // connect to s3
     S3 s3 = new S3();
     S3Client s3Client = s3.getS3Client();

     byte[] document = s3.getObjectBytes(s3Client, bucketName, filePath);
     // Convert Document from dir the byte array
     DSSDocument toSignDocument = new InMemoryDocument(document);

     // Get token from certificate
     Pkcs12SignatureToken signingToken = new Pkcs12SignatureToken(certPath, new PasswordProtection(pass.toCharArray()));
     DSSPrivateKeyEntry privateKey = signingToken.getKeys().get(0);

     // Preparing parameters for the PAdES signature
     PAdESSignatureParameters parameters = new PAdESSignatureParameters();

     // We choose the level of the signature (-B, -T, -LT, -LTA).
     parameters.setSignatureLevel(SignatureLevel.PAdES_BASELINE_B);

     // We set the signing certificate
     parameters.setSigningCertificate(privateKey.getCertificate());

     // We set the certificate chain
     parameters.setCertificateChain(privateKey.getCertificateChain());

     // Set all the image and text params for the signature
     parameters.setImageParameters(setImageParams());

     // Create common certificate verifier
     CommonCertificateVerifier commonCertificateVerifier = new CommonCertificateVerifier();

     // Create PAdESService for signature
     PAdESService service = new PAdESService(commonCertificateVerifier);
     service.setPdfObjFactory(new PdfBoxNativeObjectFactory());

     // Get the SignedInfo segment that need to be signed.
     ToBeSigned dataToSign = service.getDataToSign(toSignDocument, parameters);

     // This function obtains the signature value for signed information using the
     // private key and specified algorithm
     DigestAlgorithm digestAlgorithm = parameters.getDigestAlgorithm();
     SignatureValue signatureValue = signingToken.sign(dataToSign, digestAlgorithm, privateKey);

     // We invoke the xadesService to sign the document with the signature value obtained in
     // the previous step.
     DSSDocument signedDocument = service.signDocument(toSignDocument, parameters, signatureValue);

    //     signedDocument.save("temp.pdf");
     byte[] output = Utils.toByteArray(signedDocument.openStream());
     S3.putS3Object(s3Client, bucketName, filePath, output);
     return "OK";

    }
}
