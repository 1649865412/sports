package com.innshine.shopAnalyse.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CodeTest
{

	TimeTool timeTool = new TimeTool();

	@Before
	public void setUp() throws Exception
	{
		System.out.println(new UUIDTest());
		System.out.println("begin:----------------" + DateUtil.getLocalDate());
		/*
		 * System.out.println("可用虚拟机线程数量" +
		 * Runtime.getRuntime().availableProcessors());
		 */
		System.out.println(new UUIDTest());
	}

	@After
	public void tearDown() throws Exception
	{
		// Nine();
		System.out.println("end:----------------" + DateUtil.getLocalDate());
	}

	@Test
	public void test()
	{
		String str = timeTool.junitText("text");
		assertEquals(str, "text");
		assertNotNull(str);

		List list = new ArrayList();
		list.add("1");
		list.add("2");
		list.add("3");
		try
		{
			Thread.sleep(1000 * 10);
		} catch (Exception e)
		{
			System.out.println("线程有误");
		}
		Iterator it = list.iterator();
		while (it.hasNext())
		{
			// System.out.println("迭代器"+it.next());
			if (it.next().equals("2"))
			{
				it.remove();
			}

		}

		for (int i = 0; i < list.size(); i++)
		{
			System.out.println(list.get(i));
		}
		// assertn
	}

	class UUIDTest
	{
		public UUIDTest()
		{
			text();
			// MD5("杨荣忠");

		}

		public void text()
		{
			UUID uuid = UUID.randomUUID();
			System.out.println(uuid.toString().replace("-", ""));
		}
	}

	public void Nine()
	{
		for (int i = 1; i <= 9; i++)
		{
			for (int j = 1; j <= i; j++)
			{
				System.out.print(j + "*" + i + "=" + i * j + " ");
			}
			System.out.println("\n");// 必须在第二个for循环之外
		}

	}

}
