package io.swagger.sign;

 import eu.europa.esig.dss.model.InMemoryDocument;
 import eu.europa.esig.dss.pades.PAdESSignatureParameters;
 import eu.europa.esig.dss.enumerations.SignatureLevel;
 import eu.europa.esig.dss.enumerations.DigestAlgorithm;
 import eu.europa.esig.dss.pades.SignatureFieldParameters;
 import eu.europa.esig.dss.pades.SignatureImageParameters;
 import eu.europa.esig.dss.pdf.pdfbox.PdfBoxNativeObjectFactory;
 import eu.europa.esig.dss.validation.CommonCertificateVerifier;
 import eu.europa.esig.dss.pades.signature.PAdESService;
 import eu.europa.esig.dss.model.ToBeSigned;
 import eu.europa.esig.dss.model.SignatureValue;
 import eu.europa.esig.dss.model.DSSDocument;

 import java.io.IOException;
 import java.security.KeyStore.PasswordProtection;
 import java.util.List;
 import eu.europa.esig.dss.enumerations.DigestAlgorithm;
 import eu.europa.esig.dss.model.SignatureValue;
 import eu.europa.esig.dss.model.ToBeSigned;
 import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
 import eu.europa.esig.dss.token.Pkcs12SignatureToken;
 import eu.europa.esig.dss.utils.Utils;
 import static org.hibernate.validator.internal.util.Contracts.assertTrue;

public class Sign {
    public DSSDocument signDoc(DSSDocument toSignDocument) throws IOException {
        Pkcs12SignatureToken signingToken = new Pkcs12SignatureToken("src/main/resources/user_a_rsa.p12", new PasswordProtection("password".toCharArray()));
        DSSPrivateKeyEntry privateKey = signingToken.getKey("test");
        // Preparing parameters for the PAdES signature
        PAdESSignatureParameters parameters = new PAdESSignatureParameters();
        // We choose the level of the signature (-B, -T, -LT, -LTA).
        parameters.setSignatureLevel(SignatureLevel.PAdES_BASELINE_B);
        // We set the digest algorithm to use with the signature algorithm. You must use the
        // same parameter when you invoke the method sign on the token. The default value is
        // SHA256
        parameters.setDigestAlgorithm(DigestAlgorithm.SHA256);

        // We set the signing certificate
        parameters.setSigningCertificate(privateKey.getCertificate());
        // We set the certificate chain
        parameters.setCertificateChain(privateKey.getCertificateChain());

        // Create common certificate verifier
        CommonCertificateVerifier commonCertificateVerifier = new CommonCertificateVerifier();
        // Create PAdESService for signature
        PAdESService service = new PAdESService(commonCertificateVerifier);

        // Get the SignedInfo segment that need to be signed.
        ToBeSigned dataToSign = service.getDataToSign(toSignDocument, parameters);

        // This function obtains the signature value for signed information using the
        // private key and specified algorithm
        DigestAlgorithm digestAlgorithm = parameters.getDigestAlgorithm();
        SignatureValue signatureValue = signingToken.sign(dataToSign, digestAlgorithm, privateKey);

        // Optionally or for debug purpose :
        // Validate the signature value against the original dataToSign
//        assertTrue(service.isValidSignatureValue(dataToSign, signatureValue, privateKey.getCertificate()));

        // We invoke the padesService to sign the document with the signature value obtained in
        // the previous step.

        return service.signDocument(toSignDocument, parameters, signatureValue);
    }
}
