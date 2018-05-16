/**
 * 
 */
package org.edtoktay.dynamic.compiler.utils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;

import javax.tools.JavaFileObject;

import org.edtoktay.dynamic.compiler.impl.DynamicJavaFileObject;

/**
 * @author deniz.toktay
 *
 */
public class InternalClassFinder {
	private ClassLoader classLoader;
	private static final String CLASS_EXTENSION = ".class";

	public InternalClassFinder(ClassLoader classLoader) {
		super();
		this.classLoader = classLoader;
	}

	public Iterable<JavaFileObject> find(String packageName) throws IOException {
		String javaPackageName = packageName.replaceAll("\\.", "/");
		List<JavaFileObject> result = new ArrayList<JavaFileObject>();
		Enumeration<URL> urlEnumeration = classLoader.getResources(javaPackageName);
		while (urlEnumeration.hasMoreElements()) {
			URL packageFolderURL = urlEnumeration.nextElement();
			result.addAll(listUnder(packageName, packageFolderURL));
		}
		return result;
	}

	private Collection<? extends JavaFileObject> listUnder(String packageName, URL packageFolderURL) {
		File directory = new File(packageFolderURL.getFile());
		if (directory.isDirectory())
			return processDir(packageName, directory);
		else
			return processJar(packageFolderURL);
	}

	private Collection<? extends JavaFileObject> processJar(URL packageFolderURL) {
		List<JavaFileObject> result = new ArrayList<JavaFileObject>();
		try {
			String jarUri = packageFolderURL.toExternalForm().substring(0,
					packageFolderURL.toExternalForm().lastIndexOf("!"));
			JarURLConnection jarConn = (JarURLConnection) packageFolderURL.openConnection();
			String rootEntryName = jarConn.getEntryName();
			int rootEnd = rootEntryName.length() + 1;
			Enumeration<JarEntry> entryEnum = jarConn.getJarFile().entries();
			while (entryEnum.hasMoreElements()) {
				JarEntry jarEntry = entryEnum.nextElement();
				String name = jarEntry.getName();
				if (name.startsWith(rootEntryName) && name.indexOf('/', rootEnd) == -1
						&& name.endsWith(CLASS_EXTENSION)) {
					URI uri = URI.create(jarUri + "!/" + name);
					String binaryName = name.replaceAll("/", ".");
					binaryName = binaryName.replaceAll(CLASS_EXTENSION + "$", "");
					result.add(new DynamicJavaFileObject(binaryName, uri));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Wasn't able to open " + packageFolderURL + " as a jar file", e);
		}
		return result;
	}

	private Collection<? extends JavaFileObject> processDir(String packageName, File directory) {
		List<JavaFileObject> result = new ArrayList<JavaFileObject>();
		File[] childFiles = directory.listFiles();
		for (File childFile : childFiles) {
			if (childFile.isFile()) {
				if (childFile.getName().endsWith(CLASS_EXTENSION)) {
					String binaryName = packageName + "." + childFile.getName();
					binaryName = binaryName.replaceAll(CLASS_EXTENSION + "$", "");
					result.add(new DynamicJavaFileObject(binaryName, childFile.toURI()));
				}
			}
		}
		return result;
	}

}
