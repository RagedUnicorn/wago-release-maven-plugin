package com.ragedunicorn.tools.maven;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

public class WagoReleaseWagoClientTest {
  private static final String ENDPOINT = "https://addons.wago.io/api/projects/:projectId/version";
  private static final String PROJECT_ID = "111111";
  private static final String TOKEN = "test-token";

  @Test
  public void prepareEndpointUri_defaultBaseUri_returnsReplacedUri() throws MojoExecutionException {
    WagoClient client = new WagoClient();
    client.setProjectId(PROJECT_ID);
    client.setToken(TOKEN);

    URI preparedUri = client.prepareEndpointUri(ENDPOINT);

    Assert.assertEquals(
        "https://addons.wago.io/api/projects/" + PROJECT_ID + "/version",
        preparedUri.toString()
    );
  }

  @Test
  public void prepareEndpointUri_customBaseUri_returnsReplacedUri() throws MojoExecutionException {
    WagoClient client = new WagoClient();
    client.setBaseUri("https://example.test/v2/:projectId/foo");
    client.setProjectId(PROJECT_ID);
    client.setToken(TOKEN);

    URI preparedUri = client.prepareEndpointUri("/some/path");

    Assert.assertEquals(
        "https://example.test/v2/" + PROJECT_ID + "/foo",
        preparedUri.toString()
    );
  }

  @Test(expected = IllegalStateException.class)
  public void prepareEndpointUri_uninitializedClient_throwsIllegalStateException()
      throws MojoExecutionException {
    WagoClient client = new WagoClient();
    client.prepareEndpointUri("/some/url");
  }

  @Test(expected = IllegalStateException.class)
  public void getHttpClient_uninitializedClient_throwsIllegalStateException() {
    WagoClient client = new WagoClient();
    client.getHttpClient();
  }

  @Test(expected = IllegalStateException.class)
  public void prepareEndpointUri_emptyToken_throwsIllegalStateException()
      throws MojoExecutionException {
    WagoClient client = new WagoClient();
    client.setProjectId(PROJECT_ID);
    client.setToken("");

    client.prepareEndpointUri(ENDPOINT);
  }

  @Test(expected = IllegalStateException.class)
  public void prepareEndpointUri_emptyProjectId_throwsIllegalStateException()
      throws MojoExecutionException {
    WagoClient client = new WagoClient();
    client.setProjectId("");
    client.setToken(TOKEN);

    client.prepareEndpointUri(ENDPOINT);
  }

  @Test
  public void getHttpClient_validPreconditions_returnsNonNullClient() throws IOException {
    WagoClient client = new WagoClient();
    client.setProjectId(PROJECT_ID);
    client.setToken(TOKEN);

      try (CloseableHttpClient httpClient = client.getHttpClient()) {
          Assert.assertNotNull(httpClient);
      }
  }
}