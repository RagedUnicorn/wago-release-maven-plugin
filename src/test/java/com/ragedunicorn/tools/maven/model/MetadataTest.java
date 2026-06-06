package com.ragedunicorn.tools.maven.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MetadataTest {

  private Gson gson;
  private Metadata metadata;

  @Before
  public void setUp() {
    gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    metadata = new Metadata();
    metadata.setLabel("example-upload");
    metadata.setStability("stable");
    metadata.setChangelog("# Notes");
    metadata.setSupportedRetailPatch("10.2.0");
    metadata.setSupportedMopPatch("5.5.0");
    metadata.setSupportedCataPatch("4.4.0");
    metadata.setSupportedWotlkcPatch("3.4.3");
    metadata.setSupportedBccPatch("2.5.4");
    metadata.setSupportedClassicPatch("1.13.7");
  }

  @Test
  public void toJson_thenFromJson_returnsEqualMetadata() {
    String json = gson.toJson(metadata);
    Metadata roundtripped = gson.fromJson(json, Metadata.class);

    Assert.assertEquals(metadata, roundtripped);
  }

  @Test
  public void toJson_supportedPatchFields_useSnakeCaseFieldNames() {
    String json = gson.toJson(metadata);

    Assert.assertTrue("missing supported_retail_patch in: " + json,
        json.contains("\"supported_retail_patch\""));
    Assert.assertTrue("missing supported_mop_patch in: " + json,
        json.contains("\"supported_mop_patch\""));
    Assert.assertTrue("missing supported_cata_patch in: " + json,
        json.contains("\"supported_cata_patch\""));
    Assert.assertTrue("missing supported_wotlk_patch in: " + json,
        json.contains("\"supported_wotlk_patch\""));
    Assert.assertTrue("missing supported_bc_patch in: " + json,
        json.contains("\"supported_bc_patch\""));
    Assert.assertTrue("missing supported_classic_patch in: " + json,
        json.contains("\"supported_classic_patch\""));

    Assert.assertFalse("camelCase field name leaked into json: " + json,
        json.contains("supportedRetailPatch"));
    Assert.assertFalse("camelCase field name leaked into json: " + json,
        json.contains("supportedMopPatch"));
    Assert.assertFalse("camelCase field name leaked into json: " + json,
        json.contains("supportedCataPatch"));
    Assert.assertFalse("camelCase field name leaked into json: " + json,
        json.contains("supportedWotlkcPatch"));
    Assert.assertFalse("camelCase field name leaked into json: " + json,
        json.contains("supportedBccPatch"));
    Assert.assertFalse("camelCase field name leaked into json: " + json,
        json.contains("supportedClassicPatch"));
  }
}