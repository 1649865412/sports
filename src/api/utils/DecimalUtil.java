package api.utils;

import java.math.BigDecimal;

public class DecimalUtil
{
	
	public static double add(double d1, double d2)
	{ // 进行加法计算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.add(b2).doubleValue();
	}
	
	public static double sub(double d1, double d2)
	{ // 进行减法计算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.subtract(b2).doubleValue();
	}
	
	public static double mul(double d1, double d2)
	{ // 进行乘法计算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.multiply(b2).doubleValue();
	}
	
	public static double div(double d1, double d2, int len)
	{ // 进行除法计算
		if (d2 == 0)
		{
			return 0.00;
		}
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static double round(double d, int len)
	{ // 进行四舍五入
		BigDecimal b1 = new BigDecimal(d);
		BigDecimal b2 = new BigDecimal(1);
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
}
