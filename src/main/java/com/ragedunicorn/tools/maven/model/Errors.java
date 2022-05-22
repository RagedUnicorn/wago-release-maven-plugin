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

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Errors {
  public List<String> metadata = new ArrayList<>();
  public List<String> file = new ArrayList<>();
  public List<String> stability = new ArrayList<>();
  @SerializedName(value = "supported_retail_patch")
  public List<String> supportedRetailPatch = new ArrayList<>();
  @SerializedName(value = "supported_bc_patch")
  public List<String> supportedBccPatch = new ArrayList<>();
  @SerializedName(value = "supported_classic_patch")
  public List<String> supportedClassicPatch = new ArrayList<>();

  public List<String> getMetadata() {
    return metadata;
  }

  public void setMetadata(List<String> metadata) {
    this.metadata = metadata;
  }

  public List<String> getFile() {
    return file;
  }

  public void setFile(List<String> file) {
    this.file = file;
  }

  public List<String> getStability() {
    return stability;
  }

  public void setStability(List<String> stability) {
    this.stability = stability;
  }

  public List<String> getSupportedRetailPatch() {
    return supportedRetailPatch;
  }

  public void setSupportedRetailPatch(List<String> supportedRetailPatch) {
    this.supportedRetailPatch = supportedRetailPatch;
  }

  public List<String> getSupportedBccPatch() {
    return supportedBccPatch;
  }

  public void setSupportedBccPatch(List<String> supportedBccPatch) {
    this.supportedBccPatch = supportedBccPatch;
  }

  public List<String> getSupportedClassicPatch() {
    return supportedClassicPatch;
  }

  public void setSupportedClassicPatch(List<String> supportedClassicPatch) {
    this.supportedClassicPatch = supportedClassicPatch;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Errors errors = (Errors) o;
    return Objects.equals(metadata, errors.metadata)
            && Objects.equals(file, errors.file)
            && Objects.equals(stability, errors.stability)
            && Objects.equals(supportedRetailPatch, errors.supportedRetailPatch)
            && Objects.equals(supportedBccPatch, errors.supportedBccPatch)
            && Objects.equals(supportedClassicPatch, errors.supportedClassicPatch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(metadata, file, stability, supportedRetailPatch, supportedBccPatch, supportedClassicPatch);
  }

  @Override
  public String toString() {
    return "Errors{"
            + "metadata=" + metadata
            + ", file=" + file
            + ", stability=" + stability
            + ", supportedRetailPatch=" + supportedRetailPatch
            + ", supportedBccPatch=" + supportedBccPatch
            + ", supportedClassicPatch=" + supportedClassicPatch
            + '}';
  }
}
