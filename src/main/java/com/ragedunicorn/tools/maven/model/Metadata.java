/*
 * Copyright (c) 2022 Michael Wiesendanger
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:

 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.ragedunicorn.tools.maven.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class Metadata {

  @Expose
  private String label;

  @Expose
  private String stability;

  @Expose
  private String changelog;

  @Expose
  @SerializedName(value = "supported_retail_patch")
  private String supportedRetailPatch;

  @Expose
  @SerializedName(value = "supported_bc_patch")
  private String supportedBccPatch;

  @Expose
  @SerializedName(value = "supported_classic_patch")
  private String supportedClassicPatch;

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getStability() {
    return stability;
  }

  public void setStability(String stability) {
    this.stability = stability;
  }

  public String getChangelog() {
    return changelog;
  }

  public void setChangelog(String changelog) {
    this.changelog = changelog;
  }

  public String getSupportedRetailPatch() {
    return supportedRetailPatch;
  }

  public void setSupportedRetailPatch(String supportedRetailPatch) {
    this.supportedRetailPatch = supportedRetailPatch;
  }

  public String getSupportedBccPatch() {
    return supportedBccPatch;
  }

  public void setSupportedBccPatch(String supportedBccPatch) {
    this.supportedBccPatch = supportedBccPatch;
  }

  public String getSupportedClassicPatch() {
    return supportedClassicPatch;
  }

  public void setSupportedClassicPatch(String supportedClassicPatch) {
    this.supportedClassicPatch = supportedClassicPatch;
  }

  @Override
  public String toString() {
    return "Metadata{"
            + "label='" + label + '\''
            + ", stability='" + stability + '\''
            + ", changelog='" + changelog + '\''
            + ", supportedRetailPatch='" + supportedRetailPatch + '\''
            + ", supportedBccPatch='" + supportedBccPatch + '\''
            + ", supportedClassicPatch='" + supportedClassicPatch + '\''
            + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Metadata metadata = (Metadata) o;
    return Objects.equals(label, metadata.label)
            && Objects.equals(stability, metadata.stability)
            && Objects.equals(changelog, metadata.changelog)
            && Objects.equals(supportedRetailPatch, metadata.supportedRetailPatch)
            && Objects.equals(supportedBccPatch, metadata.supportedBccPatch)
            && Objects.equals(supportedClassicPatch, metadata.supportedClassicPatch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, stability, changelog, supportedRetailPatch, supportedBccPatch, supportedClassicPatch);
  }
}
