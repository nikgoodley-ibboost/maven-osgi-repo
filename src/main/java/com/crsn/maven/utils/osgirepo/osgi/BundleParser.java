package com.crsn.maven.utils.osgirepo.osgi;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;

import org.eclipse.osgi.util.ManifestElement;
import org.osgi.framework.BundleException;
import org.osgi.framework.Version;

public class BundleParser {

	private final String pluginName;
	private final Version pluginVersion;

	private final LinkedList<OsgiDependency> requiredBundles;

	public BundleParser(InputStream content) {
		try {

			HashMap<String, String> headers = new HashMap<String, String>();
			ManifestElement.parseBundleManifest(content, headers);

			ManifestElement[] elements = ManifestElement.parseHeader(
					"Bundle-SymbolicName", headers.get("Bundle-SymbolicName"));
			if (elements.length != 1) {
				throw new IllegalArgumentException(
						"more than one symbolic name");
			}
			this.pluginName = elements[0].getValue();
			this.pluginVersion = new Version(headers.get("Bundle-Version"));

			this.requiredBundles = new LinkedList<OsgiDependency>();

			ManifestElement[] requireElements = ManifestElement.parseHeader(
					"Require-Bundle", headers.get("Require-Bundle"));

			if (requireElements != null) {
				for (ManifestElement manifestElement : requireElements) {

					String bundleVersion = manifestElement
							.getAttribute("bundle-version");
					String bundleName = manifestElement.getValue();

					requiredBundles.add(new OsgiDependency(bundleName,
							VersionRange.parseVersionRange(bundleVersion)));
				}
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (BundleException e) {
			throw new RuntimeException(e);
		}
	}

	public String getPluginName() {
		return pluginName;
	}

	public Version getPluginVersion() {
		return pluginVersion;
	}

	public LinkedList<OsgiDependency> getRequiredBundles() {
		return requiredBundles;
	}

}
