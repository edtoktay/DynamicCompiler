/**
 * 
 */
package org.edtoktay.dynamic.compiler.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.tools.SimpleJavaFileObject;

import org.edtoktay.dynamic.compiler.DynamicCompiler;
import org.edtoktay.dynamic.compiler.exceptions.NotImplementedException;

/**
 * @author deniz.toktay
 *
 */
public class DynamicSimpleFileObject extends SimpleJavaFileObject {
	private ByteArrayOutputStream byteCode;
	private final CharSequence source;

	public DynamicSimpleFileObject(final String className, final CharSequence javaSource) {
		super(DynamicCompiler.toURI(className + DynamicCompiler.JAVA_EXTENSION), Kind.SOURCE);
		this.source = javaSource;
	}

	public DynamicSimpleFileObject(String qualifiedName, Kind kind) {
		super(DynamicCompiler.toURI(qualifiedName), Kind.SOURCE);
		this.source = null;
	}

	@Override
	public InputStream openInputStream() throws IOException {
		return new ByteArrayInputStream(getByteCode());
	}

	@Override
	public OutputStream openOutputStream() throws IOException {
		byteCode = new ByteArrayOutputStream();
		return byteCode;
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		if (source == null)
			throw new NotImplementedException("getCharContent()");
		return source;
	}

	byte[] getByteCode() {
		return byteCode.toByteArray();
	}
}
