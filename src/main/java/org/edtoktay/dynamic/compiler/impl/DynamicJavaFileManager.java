/**
 * 
 */
package org.edtoktay.dynamic.compiler.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;

import org.edtoktay.dynamic.compiler.utils.InternalClassFinder;

/**
 * @author deniz.toktay
 *
 */
public class DynamicJavaFileManager implements JavaFileManager {
	private final ClassLoader classLoader;
	private final StandardJavaFileManager standardJavaFileManager;
	private final InternalClassFinder internalClassFinder;

	public DynamicJavaFileManager(ClassLoader classLoader, StandardJavaFileManager standardJavaFileManager) {
		super();
		this.classLoader = classLoader;
		this.standardJavaFileManager = standardJavaFileManager;
		this.internalClassFinder = new InternalClassFinder(classLoader);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.OptionChecker#isSupportedOption(java.lang.String)
	 */
	@Override
	public int isSupportedOption(String option) {
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.tools.JavaFileManager#getClassLoader(javax.tools.JavaFileManager.
	 * Location)
	 */
	@Override
	public ClassLoader getClassLoader(Location location) {
		return classLoader;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.tools.JavaFileManager#list(javax.tools.JavaFileManager.Location,
	 * java.lang.String, java.util.Set, boolean)
	 */
	@Override
	public Iterable<JavaFileObject> list(Location location, String packageName, Set<Kind> kinds, boolean recurse)
			throws IOException {
		if (location == StandardLocation.PLATFORM_CLASS_PATH)
			return standardJavaFileManager.list(location, packageName, kinds, recurse);
		else if (location == StandardLocation.CLASS_PATH && kinds.contains(JavaFileObject.Kind.CLASS)) {
			if (packageName.startsWith("java."))
				return standardJavaFileManager.list(location, packageName, kinds, recurse);
			else
				return internalClassFinder.find(packageName);
		}
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.tools.JavaFileManager#inferBinaryName(javax.tools.JavaFileManager.
	 * Location, javax.tools.JavaFileObject)
	 */
	@Override
	public String inferBinaryName(Location location, JavaFileObject file) {
		if (file instanceof DynamicJavaFileObject)
			return ((DynamicJavaFileObject) file).binaryName();
		else
			return standardJavaFileManager.inferBinaryName(location, file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.JavaFileManager#isSameFile(javax.tools.FileObject,
	 * javax.tools.FileObject)
	 */
	@Override
	public boolean isSameFile(FileObject a, FileObject b) {
		return standardJavaFileManager.isSameFile(a, b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.JavaFileManager#handleOption(java.lang.String,
	 * java.util.Iterator)
	 */
	@Override
	public boolean handleOption(String current, Iterator<String> remaining) {
		return standardJavaFileManager.handleOption(current, remaining);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.JavaFileManager#hasLocation(javax.tools.JavaFileManager.
	 * Location)
	 */
	@Override
	public boolean hasLocation(Location location) {
		return location == StandardLocation.CLASS_PATH || location == StandardLocation.PLATFORM_CLASS_PATH;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.JavaFileManager#getJavaFileForInput(javax.tools.
	 * JavaFileManager.Location, java.lang.String,
	 * javax.tools.JavaFileObject.Kind)
	 */
	@Override
	public JavaFileObject getJavaFileForInput(Location location, String className, Kind kind) throws IOException {
		return standardJavaFileManager.getJavaFileForInput(location, className, kind);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.JavaFileManager#getJavaFileForOutput(javax.tools.
	 * JavaFileManager.Location, java.lang.String,
	 * javax.tools.JavaFileObject.Kind, javax.tools.FileObject)
	 */
	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling)
			throws IOException {
		return standardJavaFileManager.getJavaFileForOutput(location, className, kind, sibling);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.tools.JavaFileManager#getFileForInput(javax.tools.JavaFileManager.
	 * Location, java.lang.String, java.lang.String)
	 */
	@Override
	public FileObject getFileForInput(Location location, String packageName, String relativeName) throws IOException {
		return standardJavaFileManager.getFileForInput(location, packageName, relativeName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.tools.JavaFileManager#getFileForOutput(javax.tools.JavaFileManager.
	 * Location, java.lang.String, java.lang.String, javax.tools.FileObject)
	 */
	@Override
	public FileObject getFileForOutput(Location location, String packageName, String relativeName, FileObject sibling)
			throws IOException {
		return standardJavaFileManager.getFileForOutput(location, packageName, relativeName, sibling);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.JavaFileManager#flush()
	 */
	@Override
	public void flush() throws IOException {
		standardJavaFileManager.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.JavaFileManager#close()
	 */
	@Override
	public void close() throws IOException {
		standardJavaFileManager.close();
	}

}
