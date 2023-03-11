package io.swagger.sign;

 import eu.europa.esig.dss.enumerations.TextWrapping;
 import eu.europa.esig.dss.model.InMemoryDocument;
 import eu.europa.esig.dss.pades.*;
 import eu.europa.esig.dss.enumerations.SignatureLevel;
 import eu.europa.esig.dss.enumerations.DigestAlgorithm;
 import eu.europa.esig.dss.pdf.pdfbox.PdfBoxNativeObjectFactory;
 import eu.europa.esig.dss.validation.CommonCertificateVerifier;
 import eu.europa.esig.dss.pades.signature.PAdESService;
 import eu.europa.esig.dss.model.ToBeSigned;
 import eu.europa.esig.dss.model.SignatureValue;
 import eu.europa.esig.dss.model.DSSDocument;
 import java.io.File;
 import java.nio.file.Files;
 import java.awt.*;
 import java.io.IOException;
 import java.nio.file.Paths;
 import java.security.KeyStore.PasswordProtection;
 import java.sql.SQLOutput;

 import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
 import eu.europa.esig.dss.token.Pkcs12SignatureToken;

public class Sign {
    public String signDoc(String filePath, String certPath, String pass) throws IOException {
        System.out.println("CREATING DOCUMENT");
        byte[] array = Files.readAllBytes(Paths.get(filePath));
        DSSDocument toSignDocument = new InMemoryDocument(array);
        System.out.println("DOCUMENT ASSIGNED");
        Pkcs12SignatureToken signingToken = new Pkcs12SignatureToken(certPath, new PasswordProtection(pass.toCharArray()));
        DSSPrivateKeyEntry privateKey = signingToken.getKeys().get(0);
        // Preparing parameters for the PAdES signature
        PAdESSignatureParameters parameters = new PAdESSignatureParameters();
        // We choose the level of the signature (-B, -T, -LT, -LTA).
        System.out.println("PRIVATE KEY CREATED");
        parameters.setSignatureLevel(SignatureLevel.PAdES_BASELINE_B);

// We set the signing certificate
        parameters.setSigningCertificate(privateKey.getCertificate());
// We set the certificate chain
        parameters.setCertificateChain(privateKey.getCertificateChain());


// Initialize visual signature and configure
        SignatureImageParameters imageParameters = new SignatureImageParameters();
// Instantiates a SignatureImageTextParameters object
        SignatureImageTextParameters textParameters = new SignatureImageTextParameters();
        SignatureFieldParameters fieldParameters = new SignatureFieldParameters();
        imageParameters.setFieldParameters(fieldParameters);

// Allows defining of a specific page in a PDF document where the signature must be placed.
// The counting of pages starts from 1 (the first page)
// (the default value = 1).
        fieldParameters.setPage(0);

// Absolute positioning functions, allowing to specify a margin between
// the left page side and the top page side respectively, and
// a signature field (if no rotation and alignment is applied).
        fieldParameters.setOriginX(10);
        fieldParameters.setOriginY(10);

// Allows specifying of a precise signature field's width in pixels.
// If not defined, the default image/text width will be used.
        fieldParameters.setWidth(100);

// Allows specifying of a precise signature field's height in pixels.
// If not defined, the default image/text height will be used.
        fieldParameters.setHeight(125);
// Allows you to set a DSSFont object that defines the text style (see more information in the section "Fonts usage")
        DSSFont font = new DSSJavaFont(Font.SERIF);
        font.setSize(16); // Specifies the text size value (the default font size is 12pt)
        textParameters.setFont(font);
// Defines the text content
        textParameters.setText("My visual signature \n #1");
// Defines the color of the characters
        textParameters.setTextColor(Color.BLUE);
// Defines the background color for the area filled out by the text
        textParameters.setBackgroundColor(Color.YELLOW);
// Defines a padding between the text and a border of its bounding area
        textParameters.setPadding(20);
// TextWrapping parameter allows defining the text wrapping behavior within  the signature field
/*
  FONT_BASED - the default text wrapping, the text is computed based on the given font size;
  FILL_BOX - finds optimal font size to wrap the text to a signature field box;
  FILL_BOX_AND_LINEBREAK - breaks the words to multiple lines in order to find the biggest possible font size to wrap the text into a signature field box.
*/
        textParameters.setTextWrapping(TextWrapping.FONT_BASED);
// Set textParameters to a SignatureImageParameters object
        imageParameters.setTextParameters(textParameters);



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
        System.out.println(signedDocument.getName());
        System.out.println("FINSIHED");

        signedDocument.save("/home/aledmin/Downloads/signed.pdf");
        return "OK";
    }


}
