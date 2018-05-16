package org.edtoktay.dynamic.compiler;

import static org.junit.Assert.*;

import org.edtoktay.dynamic.compiler.exceptions.DynamicCompilerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test1 {

	@Test
	public void test() throws InstantiationException, IllegalAccessException, DynamicCompilerException {
		UseExample1 use1 = new UseExample1();
		use1.addObj("asd", "df");
		assertNotNull(use1.getObject("asd"));
	}

}
