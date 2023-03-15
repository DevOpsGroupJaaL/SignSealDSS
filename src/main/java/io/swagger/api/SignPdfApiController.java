package io.swagger.api;

import io.swagger.certificates.GenCert;
import io.swagger.certificates.SaveCertificate;
import io.swagger.model.Document;
import io.swagger.sign.Sign;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import eu.europa.esig.dss.model.DSSDocument;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-09T19:22:05.457058474Z[GMT]")
@RestController
public class SignPdfApiController implements SignPdfApi {

    private static final Logger log = LoggerFactory.getLogger(SignPdfApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public SignPdfApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> signDocument(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody Document body) {
        String accept = request.getHeader("Accept");
        Sign sign = new Sign();
        try
        {
            SaveCertificate sc = new SaveCertificate();
//            sc.saveCertificate("", "", "");
            String signed = sign.signDoc(body.getDocumentRef(), "/home/aledmin/Dev/JaalSigning/mycertificate.p12", "mypassword");
//            String signed = "OK";
            if (signed == "OK") {
                return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
            } else {
                return  new ResponseEntity<Void>(HttpStatus.BAD_GATEWAY);
            }
        } catch (IOException e) {
            return new ResponseEntity<Void>(HttpStatus.BAD_GATEWAY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
