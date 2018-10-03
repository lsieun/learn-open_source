package com.lsieun.lifecycle.sample;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal run-tests-goal
 * @requires Project false
 */
public class RunTestsGoalMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("run-tests-goal");
    }
}
