package io.swagger.sign;

 import eu.europa.esig.dss.pades.PAdESSignatureParameters;
 import eu.europa.esig.dss.enumerations.SignatureLevel;
 import eu.europa.esig.dss.enumerations.DigestAlgorithm;
 import eu.europa.esig.dss.validation.CommonCertificateVerifier;
 import eu.europa.esig.dss.pades.signature.PAdESService;
 import eu.europa.esig.dss.model.ToBeSigned;
 import eu.europa.esig.dss.model.SignatureValue;
 import eu.europa.esig.dss.model.DSSDocument;

public class Sign {
    // Preparing parameters for the PAdES signature
    PAdESSignatureParameters parameters = new PAdESSignatureParameters();
    //
//// We choose the level of the signature (-B, -T, -LT, -LTA).
//parameters.setSignatureLevel(SignatureLevel.PAdES_BASELINE_B);
//// We set the digest algorithm to use with the signature algorithm. You must use the
//// same parameter when you invoke the method sign on the token. The default value is
//// SHA256
//parameters.setDigestAlgorithm(DigestAlgorithm.SHA256);
//
//// We set the signing certificate
//parameters.setSigningCertificate(privateKey.getCertificate());
//// We set the certificate chain
//parameters.setCertificateChain(privateKey.getCertificateChain());

    // Create common certificate verifier
    CommonCertificateVerifier commonCertificateVerifier = new CommonCertificateVerifier();
    // Create PAdESService for signature
    PAdESService service = new PAdESService(commonCertificateVerifier);
    // Get the SignedInfo segment that need to be signed.
//    ToBeSigned dataToSign = service.getDataToSign(toSignDocument, parameters);
//
//    // This function obtains the signature value for signed information using the
//// private key and specified algorithm
//    DigestAlgorithm digestAlgorithm = parameters.getDigestAlgorithm();
//    SignatureValue signatureValue = signingToken.sign(dataToSign, digestAlgorithm, privateKey);
//
//    // Optionally or for debug purpose :
//// Validate the signature value against the original dataToSign
//    assertTrue(service.isValidSignatureValue(dataToSign, signatureValue, privateKey.getCertificate()));
//
//    // We invoke the padesService to sign the document with the signature value obtained in
//// the previous step.
//    DSSDocument signedDocument = service.signDocument(toSignDocument, parameters, signatureValue);
}
