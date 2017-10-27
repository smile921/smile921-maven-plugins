/**
 * 
 */
package org.apache.maven.plugins.dependency;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.layout.ArtifactRepositoryLayout;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.dependency.fromDependencies.AbstractFromDependenciesMojo;
import org.apache.maven.plugins.dependency.utils.DependencyStatusSets;
import org.apache.maven.plugins.dependency.utils.filters.DestFileFilter;
import org.apache.maven.shared.artifact.filter.collection.ArtifactsFilter;
import org.apache.maven.shared.artifact.install.ArtifactInstaller;

/**
 * @author frere
 *
 */
@Mojo(name = "genrepo", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class GenRepoMojo extends AbstractFromDependenciesMojo {

	@Parameter(defaultValue = "repo", property = "repo", required = false)
	private String repofolder;

	/**
	 * Also copy the pom of each artifact.
	 *
	 * @since 2.0
	 */
	@Parameter(property = "mdep.copyPom", defaultValue = "false")
	protected boolean copyPom = true;

	/**
	 *
	 */
	@Component
	private ArtifactInstaller installer;

	/**
	 *
	 */
	@Component(role = ArtifactRepositoryLayout.class)
	private Map<String, ArtifactRepositoryLayout> repositoryLayouts;

	/**
	 * Either append the artifact's baseVersion or uniqueVersion to the filename.
	 * Will only be used if {@link #isStripVersion()} is {@code false}.
	 * 
	 * @since 2.6
	 */
	@Parameter(property = "mdep.useBaseVersion", defaultValue = "true")
	protected boolean useBaseVersion = true;

	/**
	 * Add parent poms to the list of copied dependencies (both current project pom
	 * parents and dependencies parents).
	 * 
	 * @since 2.8
	 */
	@Parameter(property = "mdep.addParentPoms", defaultValue = "false")
	protected boolean addParentPoms;

	/**
	 * <i>not used in this goal</i>
	 */
	@Parameter
	protected boolean useJvmChmod = true;

	/**
	 * <i>not used in this goal</i>
	 */
	@Parameter
	protected boolean ignorePermissions;

	@Override
	protected void doExecute() throws MojoExecutionException {

		File f = outputDirectory;

		if (!f.exists()) {
			f.mkdirs();
		}
		String target = f.getAbsolutePath();
		System.out.println(target);
		File repo = new File(target + "/" + repofolder);
		if (!repo.exists()) {
			repo.mkdir();
		}

		DependencyStatusSets dss = getDependencySets(this.failOnMissingClassifierArtifact, addParentPoms);
		Set<Artifact> artifacts = dss.getResolvedDependencies();

	}

	@Override
	protected ArtifactsFilter getMarkedArtifactFilter() {
		return new DestFileFilter(this.overWriteReleases, this.overWriteSnapshots, this.overWriteIfNewer,
				this.useSubDirectoryPerArtifact, this.useSubDirectoryPerType, this.useSubDirectoryPerScope,
				this.useRepositoryLayout, this.stripVersion, this.prependGroupId, this.useBaseVersion,
				this.outputDirectory);
	}

	/**
	 * @return true, if the pom of each artifact must be copied
	 */
	public boolean isCopyPom() {
		return this.copyPom;
	}

	/**
	 * @param copyPom
	 *            - true if the pom of each artifact must be copied
	 */
	public void setCopyPom(boolean copyPom) {
		this.copyPom = copyPom;
	}

}
