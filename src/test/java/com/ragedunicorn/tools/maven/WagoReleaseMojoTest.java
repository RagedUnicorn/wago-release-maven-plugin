package com.ragedunicorn.tools.maven;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;

public class WagoReleaseMojoTest {

  @Rule
  public MojoRule rule = new MojoRule();

  @Test
  public void configureMojo_validPluginConfig_wiresAllFields() throws Exception {
    File testPom = new File(AbstractMojoTestCase.getBasedir(), "src/test/resources/plugin-config.xml");
    assertTrue(testPom.exists());

    WagoReleaseMojo mojo = new WagoReleaseMojo();
    mojo = (WagoReleaseMojo) rule.configureMojo(
        mojo, rule.extractPluginConfiguration("wago-release-maven-plugin", testPom)
    );

    assertNotNull(mojo);

    assertEquals("LvNAj96o", rule.getVariableValueFromObject(mojo, "projectId"));
    assertEquals("example-upload", rule.getVariableValueFromObject(mojo, "label"));
    assertEquals("release description overwritten by release notes",
        rule.getVariableValueFromObject(mojo, "changelog"));
    assertEquals("src/main/resources/release-notes-example.md",
        rule.getVariableValueFromObject(mojo, "changelogFile"));
    assertEquals("1.13.7", rule.getVariableValueFromObject(mojo, "supportedClassicPatch"));
    assertEquals("stable", rule.getVariableValueFromObject(mojo, "stability"));
    assertEquals("src/main/resources/asset-zip-example.zip",
        rule.getVariableValueFromObject(mojo, "file"));
    assertEquals("wago-token", rule.getVariableValueFromObject(mojo, "server"));
  }
}