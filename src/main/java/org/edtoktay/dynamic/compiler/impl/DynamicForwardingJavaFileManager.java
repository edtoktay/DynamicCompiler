/**
 * 
 */
package org.edtoktay.dynamic.compiler.impl;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardLocation;

import org.edtoktay.dynamic.compiler.DynamicCompiler;

/**
 * @author deniz.toktay
 *
 */
public class DynamicForwardingJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
	private final DynamicClassLoader classLoader;
	private final Map<URI, JavaFileObject> fileObjects = new HashMap<URI, JavaFileObject>();

	public DynamicForwardingJavaFileManager(JavaFileManager fileManager, DynamicClassLoader classLoader) {
		super(fileManager);
		this.classLoader = classLoader;
	}

	@Override
	public FileObject getFileForInput(Location location, String packageName, String relativeName) throws IOException {
		FileObject fileObject = fileObjects.get(uri(location, packageName, relativeName));
		if (fileObject != null)
			return fileObject;
		return super.getFileForInput(location, packageName, relativeName);
	}

	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String qualifiedName, Kind kind,
			FileObject outputFile) throws IOException {
		JavaFileObject file = new DynamicSimpleFileObject(qualifiedName, kind);
		classLoader.add(qualifiedName, file);
		return file;
	}

	@Override
	public ClassLoader getClassLoader(Location location) {
		return classLoader;
	}

	@Override
	public Iterable<JavaFileObject> list(Location location, String packageName, Set<Kind> kinds, boolean recurse)
			throws IOException {
		Iterable<JavaFileObject> result = super.list(location, packageName, kinds, recurse);
		ArrayList<JavaFileObject> files = new ArrayList<JavaFileObject>();
		if (location == StandardLocation.CLASS_PATH && kinds.contains(JavaFileObject.Kind.CLASS)) {
			for (JavaFileObject file : fileObjects.values()) {
				if (file.getKind() == Kind.CLASS && file.getName().startsWith(packageName))
					files.add(file);
			}
			files.addAll(classLoader.files());
		} else if (location == StandardLocation.SOURCE_PATH && kinds.contains(JavaFileObject.Kind.SOURCE)) {
			for (JavaFileObject file : fileObjects.values()) {
				if (file.getKind() == Kind.SOURCE && file.getName().startsWith(packageName))
					files.add(file);
			}
		}
		for (JavaFileObject file : result) {
			files.add(file);
		}
		return files;
	}

	@Override
	public String inferBinaryName(Location location, JavaFileObject file) {
		String result;
		if (file instanceof DynamicSimpleFileObject)
			result = file.getName();
		else
			result = super.inferBinaryName(location, file);
		return result;
	}

	public void putFileForInput(StandardLocation sourcePath, String packageName, String relativeName,
			JavaFileObject source) {
		fileObjects.put(uri(sourcePath, packageName, relativeName), source);
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	private URI uri(Location location, String packageName, String relativeName) {
		return DynamicCompiler.toURI(location.getName() + '/' + packageName + '/' + relativeName);
	}
}
