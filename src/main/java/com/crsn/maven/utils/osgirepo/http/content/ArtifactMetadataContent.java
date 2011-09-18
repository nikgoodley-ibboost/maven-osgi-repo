package com.crsn.maven.utils.osgirepo.http.content;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.maven.artifact.repository.metadata.Metadata;
import org.apache.maven.artifact.repository.metadata.Versioning;

import com.crsn.maven.utils.osgirepo.http.Content;

import com.crsn.maven.utils.osgirepo.maven.MavenVersion;

public class ArtifactMetadataContent implements Content {
	
	public ArtifactMetadataContent(String groupId, String artefactId, List<MavenVersion> versions, MavenVersion latest, MavenVersion release) {
		/*
		 * <metadata> <versioning> <release>1.0.2</release>
		 * <latest>1.0.3-SNAPSHOT</latest> <versions>
		 * <version>1.0.0-SNAPSHOT</version> <version>1.0.0</version>
		 * <version>1.0.1-SNAPSHOT</version> <version>1.0.1</version>
		 * <version>1.0.2-SNAPSHOT</version> <version>1.0.2</version>
		 * <version>1.0.3-SNAPSHOT</version> </versions> </versioning>
		 * </metadata>
		 */
		
		Metadata meta=new Metadata();
		meta.setGroupId(groupId);
		meta.setArtifactId(artefactId);
		Versioning versioning=new Versioning();
//		versioning.setLastUpdatedTimestamp(lastUpdated);
		versioning.setLatest(latest.toString());
		versioning.setRelease(release.toString());
		
		
		
	}

	@Override
	public String contentType() {
		return "text/xml";
	}

	@Override
	public long contentLength() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void serializeContent(OutputStream stream) throws IOException {

	}

}
