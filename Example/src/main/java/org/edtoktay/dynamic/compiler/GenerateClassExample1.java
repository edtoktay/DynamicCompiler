/**
 * 
 */
package org.edtoktay.dynamic.compiler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

import org.edtoktay.dynamic.compiler.exceptions.DynamicCompilerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author deniz.toktay
 *
 */
public class GenerateClassExample1 {
	private static final Logger log = LoggerFactory.getLogger(GenerateClassExample1.class);
	private final static DynamicCompiler<ExampleInterface> compiler = new DynamicCompiler<ExampleInterface>(
			GenerateClassExample1.class.getClassLoader(), Arrays.asList(new String[] { "-target", "1.8" }));

	public Class<?> generateClass() throws DynamicCompilerException {
		StringBuilder contentBuilder = new StringBuilder();
		try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource("Example1.txt").toURI()),
				StandardCharsets.UTF_8)) {
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
		} catch (URISyntaxException e) {
			log.error(e.getLocalizedMessage());
		}
		final DiagnosticCollector<JavaFileObject> errors = new DiagnosticCollector<JavaFileObject>();
		Class<?> mapperclass = compiler.compile("org.edtoktay.generated.ExampleConnect", contentBuilder.toString(), errors,
				new Class<?>[] { ExampleInterface.class });
		
		for (Diagnostic<? extends JavaFileObject> diagnostic : errors.getDiagnostics()) {
			log.error(diagnostic.getMessage(null));
		}
		return mapperclass;
	}
	
	public ExampleInterface getIst() throws InstantiationException, IllegalAccessException, DynamicCompilerException {
		Class<?> clazz = generateClass();
		
		return (ExampleInterface) clazz.newInstance();
	}
}
