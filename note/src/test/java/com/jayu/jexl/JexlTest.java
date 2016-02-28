package com.jayu.jexl;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

public class JexlTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JexlEngine jexl = new JexlEngine();
		JexlContext jc = new MapContext();
		jc.set("fee",new Fee());
		Expression exp = jexl.createExpression("fee.value('ItemFee')");
		Object value = exp.evaluate(jc);
		System.out.println(value);
		 exp = jexl.createExpression("fee.sum(1,2)");
		 value = exp.evaluate(jc);
		System.out.println(value);
	}

}
