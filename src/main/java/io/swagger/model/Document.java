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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-09T19:22:05.457058474Z[GMT]")


public class Document  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("user_id")
  private java.util.UUID userId = null;

  @JsonProperty("document_ref")
  private String documentRef = null;

  public Document userId(java.util.UUID userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
   **/
  @Schema(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", required = true, description = "")
      @NotNull

    @Valid
    public java.util.UUID getUserId() {
    return userId;
  }

  public void setUserId(java.util.UUID userId) {
    this.userId = userId;
  }

  public Document documentRef(String documentRef) {
    this.documentRef = documentRef;
    return this;
  }

  /**
   * Get documentRef
   * @return documentRef
   **/
  @Schema(example = "/aleandro/document1", required = true, description = "")
      @NotNull

    public String getDocumentRef() {
    return documentRef;
  }

  public void setDocumentRef(String documentRef) {
    this.documentRef = documentRef;
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
    return Objects.equals(this.userId, document.userId) &&
        Objects.equals(this.documentRef, document.documentRef);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, documentRef);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Document {\n");
    
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    documentRef: ").append(toIndentedString(documentRef)).append("\n");
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
