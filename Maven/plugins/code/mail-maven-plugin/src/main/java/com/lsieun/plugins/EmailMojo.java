package com.lsieun.plugins;

import java.util.Iterator;
import java.util.List;

import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

@Mojo(name="mail", requiresProject=false)
public class EmailMojo extends AbstractMojo {

    @Component
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Sending Email...");

        List<Plugin> plugins = project.getBuildPlugins();

        if (plugins != null && plugins.size() > 0) {
            Iterator<Plugin> it = plugins.iterator();
            while(it.hasNext()) {
                Plugin plugin = it.next();
                if ("mail-maven-plugin".equals(plugin.getArtifactId())) {
                    getLog().info(plugin.getConfiguration().toString());
                    break;
                }
            }
        }
    }
}
