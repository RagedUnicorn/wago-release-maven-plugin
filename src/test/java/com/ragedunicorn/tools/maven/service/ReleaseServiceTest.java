package com.ragedunicorn.tools.maven.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ragedunicorn.tools.maven.WagoClient;
import com.ragedunicorn.tools.maven.model.Metadata;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.ArgumentCaptor;

public class ReleaseServiceTest {
  private static final String BASE_URI = "https://addons.wago.io/api/projects/:projectId/version";

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private WagoClient wagoClient;
  private CloseableHttpClient httpClient;
  private CloseableHttpResponse httpResponse;
  private StatusLine statusLine;
  private File uploadFile;
  private Metadata metadata;

  @Before
  public void setUp() throws Exception {
    wagoClient = mock(WagoClient.class);
    httpClient = mock(CloseableHttpClient.class);
    httpResponse = mock(CloseableHttpResponse.class);
    statusLine = mock(StatusLine.class);

    when(wagoClient.getHttpClient()).thenReturn(httpClient);
    when(wagoClient.prepareEndpointUri(any(String.class)))
        .thenReturn(URI.create(BASE_URI.replace(":projectId", "abc")));
    when(httpClient.execute(any(HttpPost.class))).thenReturn(httpResponse);
    when(httpResponse.getStatusLine()).thenReturn(statusLine);

    uploadFile = temporaryFolder.newFile("upload.zip");

    metadata = new Metadata();
    metadata.setLabel("example-upload");
    metadata.setStability("stable");
    metadata.setSupportedClassicPatch("1.13.7");
  }

  @Test
  public void createReleaseOperation_success_returnsNormally() throws Exception {
    when(statusLine.getStatusCode()).thenReturn(201);
    when(httpResponse.getEntity()).thenReturn(entityOf("OK"));

    ReleaseService service = new ReleaseService(wagoClient);
    service.createReleaseOperation(metadata, uploadFile.getAbsolutePath());

    ArgumentCaptor<HttpPost> postCaptor = ArgumentCaptor.forClass(HttpPost.class);
    verify(httpClient).execute(postCaptor.capture());
    verify(httpClient).close();

    HttpPost captured = postCaptor.getValue();
    org.junit.Assert.assertEquals(
        URI.create("https://addons.wago.io/api/projects/abc/version"),
        captured.getURI()
    );
    org.junit.Assert.assertTrue(
        "Expected multipart entity, got: " + captured.getEntity().getContentType().getValue(),
        captured.getEntity().getContentType().getValue().startsWith("multipart/form-data")
    );
  }

  @Test
  public void createReleaseOperation_nonCreatedStatus_throwsExceptionWithExpectedMessage()
      throws Exception {
    when(statusLine.getStatusCode()).thenReturn(400);
    when(httpResponse.getEntity()).thenReturn(
        entityOf("{\"message\":\"bad\",\"errors\":{\"file\":[\"required\"]}}")
    );

    ReleaseService service = new ReleaseService(wagoClient);
    try {
      service.createReleaseOperation(metadata, uploadFile.getAbsolutePath());
      org.junit.Assert.fail("Expected MojoExecutionException");
    } catch (MojoExecutionException e) {
      org.junit.Assert.assertTrue(
          "Unexpected message: " + e.getMessage(),
          e.getMessage().startsWith("Failed to create release")
      );
    }
  }

  @Test
  public void createReleaseOperation_ioExceptionOnExecute_throwsMojoExecutionException()
      throws Exception {
    when(httpClient.execute(any(HttpPost.class))).thenThrow(new IOException("boom"));

    ReleaseService service = new ReleaseService(wagoClient);
    try {
      service.createReleaseOperation(metadata, uploadFile.getAbsolutePath());
      org.junit.Assert.fail("Expected MojoExecutionException");
    } catch (MojoExecutionException e) {
      org.junit.Assert.assertEquals("Upload to Wago.io failed", e.getMessage());
    }
  }

  private static HttpEntity entityOf(String body) {
    BasicHttpEntity entity = new BasicHttpEntity();
    entity.setContent(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));
    entity.setContentLength(body.getBytes(StandardCharsets.UTF_8).length);
    return entity;
  }
}