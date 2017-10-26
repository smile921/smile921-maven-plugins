/**
 * 
 */
package org.maven.smile921.plugin;

import java.io.File; 
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author frere
 *
 */
@Mojo( name = "genrepo", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class GenRepoMojo extends AbstractMojo {

	 /**
     * Location of the file.
     */
    @Parameter( defaultValue = "${project.build.directory}", property = "outputDir", required = true )
    private File outputDirectory;
    
	public void execute() throws MojoExecutionException, MojoFailureException {

        File f = outputDirectory;

        if ( !f.exists() )
        {
            f.mkdirs();
        }

        
	}

}
