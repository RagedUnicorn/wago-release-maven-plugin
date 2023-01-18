/*
 * Copyright (c) 2023 Michael Wiesendanger
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

package com.ragedunicorn.tools.maven;

import com.google.common.collect.Lists;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicHeader;
import org.apache.maven.plugin.MojoExecutionException;

public class WagoClient {
  private static final String USER_AGENT = "wago-release-plugin";

  private String baseUri = "https://addons.wago.io/api/projects/:projectId/version";
  // targeted project
  private String projectId;
  // token
  private String token;

  public String getBaseUri() {
    return baseUri;
  }

  public void setBaseUri(String baseUri) {
    this.baseUri = baseUri;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  /**
   * Create an http client.
   *
   * @return The created http client
   */
  public CloseableHttpClient getHttpClient() {
    hasValidPreconditions();

    return HttpClientBuilder
            .create()
            .useSystemProperties()
            .setDefaultHeaders(getDefaultHeaders())
            .setRedirectStrategy(new LaxRedirectStrategy())
            .build();
  }

  private List<Header> getDefaultHeaders() {
    return Lists.newArrayList(
            new BasicHeader(HttpHeaders.USER_AGENT, USER_AGENT),
            new BasicHeader("Authorization", "Bearer " + token),
            new BasicHeader(HttpHeaders.ACCEPT, "application/json")

    );
  }

  /**
   * Prepare an endpoint url by replacing placeholders with real values.
   *
   * @param path The path to prepare
   * @return The prepared URI
   * @throws MojoExecutionException If failing to prepare the URI properly
   */
  public URI prepareEndpointUri(final String path) throws MojoExecutionException {
    hasValidPreconditions();
    String processedBaseUri = baseUri.replace(":projectId", projectId);

    try {
      return new URI(processedBaseUri);
    } catch (URISyntaxException e) {
      throw new MojoExecutionException("Failed to prepare endpoint URI", e);
    }
  }

  /**
   * Checks if all preconditions are fulfilled.
   *
   * @throws IllegalStateException If any of the preconditions is not fulfilled
   */
  private void hasValidPreconditions() {
    if (token == null || token.isEmpty() || projectId == null || projectId.isEmpty()
            || baseUri == null || baseUri.isEmpty()) {
      throw new IllegalStateException("Wago client is in invalid state. Make sure to set token, "
              + "projectId, baseUri and the game");
    }
  }
}
