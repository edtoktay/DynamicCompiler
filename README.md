# DynamicCompiler
Dynamic Runtime Compiler Works with spring-boot Project in live environment both Linux and Windows.

Traverse through classpath jars and include classes to compilation classpath. This is an extension for D. Biesack's CharSequenceCompiler Code in order to make compiler work in the live environments. 

# Usage
In pom.xml
  ```xml
  <repositories>
		<repository>
			<id>Java-api</id>
			<url>https://packagecloud.io/edtoktay/Java-apis/maven2</url>
			<releases>
				<enabled>true</enabled>
			</releases>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
	</repositories>
  ```
  and 
  ```xml
  <dependency>
       <groupId>org.edtoktay</groupId>
       <artifactId>DynamicCompiler</artifactId>
       <version>0.0.1</version>
  </dependency>
  ```
```java
DiagnosticCollector<JavaFileObject> errs = new DiagnosticCollector<JavaFileObject>();
DynamicCompiler<IntefaceEx> compiler = new DynamicCompiler<IntefaceEx>(getClass().getClassLoader(), Arrays.asList(new String[] { "-target", "1.7" }));
String source = "package org.edtoktay.gen;\n" +
       "public class GeneratedClass implements IntefaceEx {\n" +
       "  @Override\n"
       "  public com.somepackage.SomeClass GeneratedClass(com.somepackage.AnotherClass arg0){\n " +
       "    System.out.println(\"Hello World!\");\n" +
       "  }\n" + 
       "}";
Class<IntefaceEx> clazz = compiler.compile("org.edtoktay.gen.GeneratedClass", source, errs, new Class<?>[] { IntefaceEx.class });
```

# References
 Biesack, David. "[Create Dynamic Applications With Javax.Tools](https://www.ibm.com/developerworks/library/j-jcomp/)". Ibm.com. N.p., 2007. 
