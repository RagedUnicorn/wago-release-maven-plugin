package com.ragedunicorn.tools.maven;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;

public class WagoReleaseWagoClientTest {
  @Test
  public void testEndpointUriPreparation() {
    final String ENDPOINT = "https://addons.wago.io/api/projects/:projectId/version";
    final String projectId = "111111";

    WagoClient client = new WagoClient();
    client.setProjectId(projectId);
    client.setToken("test-token");

    try {
      URI preparedUri = client.prepareEndpointUri(ENDPOINT);
      Assert.assertEquals(preparedUri.toString(), "https://addons.wago.io/api/projects/" + projectId + "/version");
    } catch (MojoExecutionException e) {
      Assert.fail("MojoExecutionException: " + Arrays.toString(e.getStackTrace()));
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testPrepareEndpointUriExpectedInvalidState() {
    WagoClient client = new WagoClient();
    try {
      client.prepareEndpointUri("/some/url");
    } catch (MojoExecutionException e) {
      Assert.fail("MojoExecutionException: " + Arrays.toString(e.getStackTrace()));
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testGetClientUriExpectedInvalidState() {
    WagoClient client = new WagoClient();
    client.getHttpClient();
  }
}
