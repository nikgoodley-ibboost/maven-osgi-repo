package com.crsn.maven.utils.osgirepo.util;

import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.crsn.maven.utils.osgirepo.http.Content;
import com.crsn.maven.utils.osgirepo.maven.MavenArtefact;
import com.crsn.maven.utils.pom.Model;
import com.crsn.maven.utils.pom.ObjectFactory;

public class PomFileContent implements Content {

	private final Model pomModel;
	private JAXBElement<Model> project;

	public PomFileContent(MavenArtefact artefact) {
		ObjectFactory factory = new ObjectFactory();
		this.pomModel = factory.createModel();
		project = factory.createProject(pomModel);

		pomModel.setArtifactId(artefact.getName());
		pomModel.setGroupId(artefact.getGroup().toString());
		pomModel.setVersion(artefact.getVersion().toString());
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
		try {
			serializeInternal(stream);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}

	}

	private void serializeInternal(OutputStream stream) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Model.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		//JAXBElement<Model> project = factory.createProject(pomModel);
		marshaller.marshal(project, stream);
	}

}
