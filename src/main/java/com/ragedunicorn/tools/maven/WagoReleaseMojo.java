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

package com.ragedunicorn.tools.maven;

import com.ragedunicorn.tools.maven.model.Metadata;
import com.ragedunicorn.tools.maven.service.ReleaseService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.settings.Server;
import org.apache.maven.settings.Settings;




@Mojo(name = "wago-release")
public class WagoReleaseMojo extends AbstractMojo {

  // The project id of the wago project (can be found on the projects page)
  @Parameter(property = "projectId", required = true)
  private String projectId;

  // A label for the uploaded file
  @Parameter(property = "label", defaultValue = "addon")
  private String label;

  // One of stable, beta, alpha
  @Parameter(property = "stability", defaultValue = "stable")
  private String stability;

  // A string containing the changelog
  @Parameter(property = "changelog")
  private String changelog;

  // Optional path to a changelog file - will override changelog
  @Parameter(property = "changelogFile")
  private String changelogFile;

  // At least one of retail, classic or bcc patch must be set (multiple values allowed)

  // The retail supported version number
  @Parameter(property = "supportRetailPatch")
  private String supportedRetailPatch;

  // The bcc supported version number
  @Parameter(property = "supportBccPatch")
  private String supportedBccPatch;

  // The classic supported version number
  @Parameter(property = "supportClassicPatch")
  private String supportedClassicPatch;

  // The path to the addon to upload
  @Parameter(property = "file", required = true)
  private String file;

  // Alternative of using a server configuration. The authToken can directly be placed in the
  // plugin configuration
  @Parameter(property = "authToken")
  private String authToken;

  // References a server configuration in your .m2 settings.xml. This is the preferred way for
  // using the generated wago token
  @Parameter(property = "server")
  private String server;

  @Parameter(defaultValue = "${settings}", readonly = true)
  private Settings settings;

  /**
   * Plugin execution callback.
   *
   * @throws MojoExecutionException If any exception happens during the execution of the plugin
   */
  public void execute() throws MojoExecutionException {
    validateRequiredInputParameters();

    WagoClient wagoClient = createWagoClient();
    createRelease(wagoClient);
  }

  /**
   * Create a new release on Wago.io.
   *
   * @param wagoClient The Wago client
   * @throws MojoExecutionException If any exception happens during the execution of the release
   *                                service
   */
  private void createRelease(WagoClient wagoClient) throws MojoExecutionException {
    final ReleaseService releaseService = new ReleaseService(wagoClient);
    Metadata metadata = new Metadata();

    metadata.setLabel(label);
    metadata.setStability(stability);
    metadata.setChangelog(getChangelog());
    metadata.setSupportedRetailPatch(supportedRetailPatch);
    metadata.setSupportedBccPatch(supportedBccPatch);
    metadata.setSupportedClassicPatch(supportedClassicPatch);

    releaseService.createReleaseOperation(metadata, file);
  }

  /**
   * Retrieve the token for the Wago.io Api.
   *
   * @return A property object containing the Wago.io Api token
   * @throws MojoExecutionException An exception occurring during the execution of a plugin
   */
  private String getCredentials() throws MojoExecutionException {
    // prefer settings parameter over direct configuration in pom
    if (settings != null && server != null) {
      final Server serverEntry = settings.getServer(server);
      if (serverEntry != null) {
        authToken = serverEntry.getPassphrase();

        if (authToken == null || authToken.isEmpty()) {
          throw new MojoExecutionException("Found server entry in settings.xml "
                  + "but authToken parameter was missing or is empty");
        }
      } else {
        getLog().warn("Unable to retrieve settings or server. Falling back to project settings");
      }
    }
    // fallback to plugin configuration if credentials cannot be retrieved from maven settings.xml
    if (authToken == null) {
      throw new MojoExecutionException("Unable to read authentication configuration make "
              + "sure to set the authToken property");
    }

    return authToken;
  }

  private String getChangelog() throws MojoExecutionException {
    if (!changelogFile.isEmpty()) {
      byte[] changelogContent;

      try {
        Path changelogPath = Paths.get(changelogFile);
        if (getLog().isDebugEnabled()) {
          getLog().debug("Changelog path: " + changelogFile);
        }
        changelogContent = Files.readAllBytes(changelogPath);

        return new String(changelogContent, StandardCharsets.UTF_8);
      } catch (IOException e) {
        throw new MojoExecutionException("Failed to read release notes", e);
      }
    } else if (!changelog.isEmpty()) {
      return changelog;
    }

    return "";
  }

  /**
   * Validate required input parameters.
   *
   * @throws MojoExecutionException An exception occurring during the execution of a plugin
   */
  private void validateRequiredInputParameters() throws MojoExecutionException {
    if (projectId == null || projectId.isEmpty()) {
      throw new MojoExecutionException("Missing required parameter projectId");
    }

    if (!hasValidSupportedVersion()) {
      throw new MojoExecutionException("Missing required parameter supported patch. One of "
              + "supportedRetailPatch, supportedBccPatch or supportedClassicPatch has to be set");
    }

    if (label == null || label.isEmpty()) {
      throw new MojoExecutionException("Missing required parameter label");
    }

    if (stability == null || stability.isEmpty()) {
      throw new MojoExecutionException("Missing required parameter stability");
    }

    if (file == null || file.isEmpty()) {
      throw new MojoExecutionException("Missing required parameter file");
    }
  }

  /**
   * Check if at least on support patch version is set.
   *
   * @return Boolean
   *      true if at least one version is set
   *      false if none of the versions is set
   */
  private Boolean hasValidSupportedVersion() {
    if (supportedRetailPatch != null && !supportedRetailPatch.isEmpty()) {
      return true;
    }

    if (supportedBccPatch != null && !supportedBccPatch.isEmpty()) {
      return true;
    }

    if (supportedClassicPatch != null && !supportedClassicPatch.isEmpty()) {
      return true;
    }

    return false;
  }

  /**
   * Create a new Wago.io client and set its owner and the targeted repository.
   * Additionally the token for authenticating against the Wago.io Api is set.
   *
   * @return The created Wago.io client
   * @throws MojoExecutionException An exception occurring during the execution of a plugin
   */
  private WagoClient createWagoClient() throws MojoExecutionException {
    WagoClient wagoClient = new WagoClient();
    wagoClient.setToken(getCredentials());
    wagoClient.setProjectId(projectId);

    return wagoClient;
  }
}
