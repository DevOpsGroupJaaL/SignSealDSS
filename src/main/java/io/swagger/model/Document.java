package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Document
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-16T09:26:44.000124225Z[GMT]")


public class Document  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("username")
  private String username = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("document_dir")
  private String documentDir = null;

  @JsonProperty("certificate_pass")
  private String certificatePass = null;

  public Document username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
   **/
  @Schema(example = "username1", required = true, description = "")
      @NotNull

    public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Document name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   **/
  @Schema(example = "Jeremy Albertson", required = true, description = "")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Document documentDir(String documentDir) {
    this.documentDir = documentDir;
    return this;
  }

  /**
   * Get documentDir
   * @return documentDir
   **/
  @Schema(example = "/user1/test.pdf", required = true, description = "")
      @NotNull

    public String getDocumentDir() {
    return documentDir;
  }

  public void setDocumentDir(String documentDir) {
    this.documentDir = documentDir;
  }

  public Document certificatePass(String certificatePass) {
    this.certificatePass = certificatePass;
    return this;
  }

  /**
   * Get certificatePass
   * @return certificatePass
   **/
  @Schema(example = "password", required = true, description = "")
      @NotNull

    public String getCertificatePass() {
    return certificatePass;
  }

  public void setCertificatePass(String certificatePass) {
    this.certificatePass = certificatePass;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Document document = (Document) o;
    return Objects.equals(this.username, document.username) &&
        Objects.equals(this.name, document.name) &&
        Objects.equals(this.documentDir, document.documentDir) &&
        Objects.equals(this.certificatePass, document.certificatePass);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, name, documentDir, certificatePass);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Document {\n");
    
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    documentDir: ").append(toIndentedString(documentDir)).append("\n");
    sb.append("    certificatePass: ").append(toIndentedString(certificatePass)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
