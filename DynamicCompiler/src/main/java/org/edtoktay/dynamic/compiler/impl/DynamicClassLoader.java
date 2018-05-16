/**
 * 
 */
package org.edtoktay.dynamic.compiler.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.tools.JavaFileObject;

/**
 * @author deniz.toktay
 *
 */
public class DynamicClassLoader extends ClassLoader {
	private final Map<String, JavaFileObject> classes = new HashMap<String, JavaFileObject>();

	public DynamicClassLoader(ClassLoader classLoader) {
		super(classLoader);
	}

	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		return super.loadClass(name, resolve);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		JavaFileObject file = classes.get(name);
		if (file != null) {
			byte[] bytes = ((DynamicSimpleFileObject) file).getByteCode();
			return defineClass(name, bytes, 0, bytes.length);
		}
		try {
			Class<?> c = Class.forName(name);
			return c;
		} catch (ClassNotFoundException nf) {
		}
		return super.findClass(name);
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		if (name.endsWith(".class")) {
			String qualifiedClassName = name.substring(0, name.length() - ".class".length()).replace('/', '.');
			DynamicSimpleFileObject file = (DynamicSimpleFileObject) classes.get(qualifiedClassName);
			if (file != null) {
				return new ByteArrayInputStream(file.getByteCode());
			}
		}
		return super.getResourceAsStream(name);
	}

	public void add(final String qualifiedName, final JavaFileObject file) {
		classes.put(qualifiedName, file);
	}

	public Collection<? extends JavaFileObject> files() {
		return Collections.unmodifiableCollection(classes.values());
	}
}
