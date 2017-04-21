/**
 * 
 */
package org.edtoktay.dynamic.compiler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;

import org.edtoktay.dynamic.compiler.exceptions.NotImplementedException;

/**
 * @author deniz.toktay
 *
 */
public class DynamicJavaFileObject implements JavaFileObject {
	private final String binaryName;
	private final URI uri;
	private final String name;

	public DynamicJavaFileObject(String binaryName, URI uri) {
		super();
		this.binaryName = binaryName;
		this.uri = uri;
		name = uri.getPath() == null ? uri.getSchemeSpecificPart() : uri.getPath();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.FileObject#toUri()
	 */
	@Override
	public URI toUri() {
		return uri;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.FileObject#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.FileObject#openInputStream()
	 */
	@Override
	public InputStream openInputStream() throws IOException {
		return uri.toURL().openStream();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.FileObject#openOutputStream()
	 */
	@Override
	public OutputStream openOutputStream() throws IOException {
		throw new NotImplementedException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.FileObject#openReader(boolean)
	 */
	@Override
	public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
		throw new NotImplementedException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.FileObject#getCharContent(boolean)
	 */
	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		throw new NotImplementedException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.FileObject#openWriter()
	 */
	@Override
	public Writer openWriter() throws IOException {
		throw new NotImplementedException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.FileObject#getLastModified()
	 */
	@Override
	public long getLastModified() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.FileObject#delete()
	 */
	@Override
	public boolean delete() {
		throw new NotImplementedException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.JavaFileObject#getKind()
	 */
	@Override
	public Kind getKind() {
		return Kind.CLASS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.JavaFileObject#isNameCompatible(java.lang.String,
	 * javax.tools.JavaFileObject.Kind)
	 */
	@Override
	public boolean isNameCompatible(String simpleName, Kind kind) {
		String baseName = simpleName + kind.extension;
		return kind.equals(getKind()) && (baseName.equals(getName()) || getName().endsWith("/" + baseName));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.JavaFileObject#getNestingKind()
	 */
	@Override
	public NestingKind getNestingKind() {
		throw new NotImplementedException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.JavaFileObject#getAccessLevel()
	 */
	@Override
	public Modifier getAccessLevel() {
		throw new NotImplementedException();
	}

	public String binaryName() {
		return binaryName;
	}

	@Override
	public String toString() {
		return "DynamicJavaFileObject{\n\turi = " + uri + "\n}";
	}

}
