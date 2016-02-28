package law.raw.StaveDrawing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

public class GeneralTestClass extends TestCase{
	
	public void testMapping(){
		Iterator i = getIterator();
		System.out.println(i.next());
	}
	
	private Iterator getIterator(){
		List list = new ArrayList();
		Object o = new Object(){
			public String toString(){
				return "This is the message";
			}
		};
		list.add(o);
		return list.iterator();
	}
}
