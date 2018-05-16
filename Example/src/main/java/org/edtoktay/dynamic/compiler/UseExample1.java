/**
 * 
 */
package org.edtoktay.dynamic.compiler;

import org.edtoktay.dynamic.compiler.exceptions.DynamicCompilerException;

/**
 * @author deniz.toktay
 * This class written to bypass Test run classloader difference with main application
 */
public class UseExample1 {
	public void addObj(String arg1, String arg2) {
		GenerateClassExample1 gce1 = new GenerateClassExample1();
		ExampleInterface exInt;
		try {
			exInt = gce1.getIst();
			exInt.addObject(arg1, arg2);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (DynamicCompilerException e) {
			e.printStackTrace();
		}
	}
	
	public MemberVM getObject(String arg1) {
		GenerateClassExample1 gce1 = new GenerateClassExample1();
		ExampleInterface exInt = null;
		try {
			exInt = gce1.getIst();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (DynamicCompilerException e) {
			e.printStackTrace();
		}
		return (MemberVM) exInt.getObject(arg1);
	}
}
