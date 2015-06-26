package com.innshine.daily.utils;

/* ====================================================================
 Licensed to the Apache Software Foundation (ASF) under one or more
 contributor license agreements.  See the NOTICE file distributed with
 this work for additional information regarding copyright ownership.
 The ASF licenses this file to You under the Apache License, Version 2.0
 (the "License"); you may not use this file except in compliance with
 the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 ==================================================================== */

import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER_SELECTION;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_FILL;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_GENERAL;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_JUSTIFY;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_LEFT;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_RIGHT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_DASHED;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_DASH_DOT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_DASH_DOT_DOT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_DOTTED;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_DOUBLE;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_HAIR;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_MEDIUM;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_MEDIUM_DASHED;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_MEDIUM_DASH_DOT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_MEDIUM_DASH_DOT_DOT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_NONE;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_SLANTED_DASH_DOT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_THICK;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN;
import static org.apache.poi.ss.usermodel.CellStyle.VERTICAL_BOTTOM;
import static org.apache.poi.ss.usermodel.CellStyle.VERTICAL_CENTER;
import static org.apache.poi.ss.usermodel.CellStyle.VERTICAL_TOP;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.examples.html.HSSFHtmlHelper;
import org.apache.poi.ss.examples.html.HtmlHelper;
import org.apache.poi.ss.examples.html.XSSFHtmlHelper;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.format.CellFormatResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.innshine.productinfo.utils.ExcelFileUtils;

/**
 * This example shows how to display a spreadsheet in HTML using the classes for
 * spreadsheet display.
 * 
 * @author Ken Arnold, Industrious Media LLC
 */
public class Excel2HtmlUtil
{
	private static final String DEFAULT_UTF_8 = "UTF-8";
	private static final String DEFAULT_EXCEL_TO_HTML = ExcelFileUtils.FILE_TEMP_DIR + File.separator + "excelToHtml"
			+ File.separator;
	private static final String HTML_SUFFIX = ".html";
	private final Workbook wb;
	private final Appendable output;
	private boolean completeHTML;
	private Formatter out;
	private boolean gotBounds;
	private int firstColumn;
	private int endColumn;
	private HtmlHelper helper;
	
	private static final String DEFAULTS_CLASS = "excelDefaults";
	private static final String COL_HEAD_CLASS = "colHeader";
	private static final String ROW_HEAD_CLASS = "rowHeader";
	
	private static final Map<Short, String> ALIGN = mapFor(ALIGN_LEFT, "left", ALIGN_CENTER, "center", ALIGN_RIGHT,
			"right", ALIGN_FILL, "left", ALIGN_JUSTIFY, "left", ALIGN_CENTER_SELECTION, "center");
	
	private static final Map<Short, String> VERTICAL_ALIGN = mapFor(VERTICAL_BOTTOM, "bottom", VERTICAL_CENTER,
			"middle", VERTICAL_TOP, "top");
	
	private static final Map<Short, String> BORDER = mapFor(BORDER_DASH_DOT, "dashed 1pt", BORDER_DASH_DOT_DOT,
			"dashed 1pt", BORDER_DASHED, "dashed 1pt", BORDER_DOTTED, "dotted 1pt", BORDER_DOUBLE, "double 3pt",
			BORDER_HAIR, "solid 1px", BORDER_MEDIUM, "solid 2pt", BORDER_MEDIUM_DASH_DOT, "dashed 2pt",
			BORDER_MEDIUM_DASH_DOT_DOT, "dashed 2pt", BORDER_MEDIUM_DASHED, "dashed 2pt", BORDER_NONE, "none",
			BORDER_SLANTED_DASH_DOT, "dashed 2pt", BORDER_THICK, "solid 3pt", BORDER_THIN, "dashed 1pt");
	
	@SuppressWarnings( { "unchecked" })
	private static <K, V> Map<K, V> mapFor(Object... mapping)
	{
		Map<K, V> map = new HashMap<K, V>();
		for (int i = 0; i < mapping.length; i += 2)
		{
			map.put((K) mapping[i], (V) mapping[i + 1]);
		}
		return map;
	}
	
	/**
	 * Creates a new converter to HTML for the given workbook.
	 * 
	 * @param wb
	 *        The workbook.
	 * @param output
	 *        Where the HTML output will be written.
	 * 
	 * @return An object for converting the workbook to HTML.
	 */
	public static Excel2HtmlUtil create(Workbook wb, Appendable output)
	{
		return new Excel2HtmlUtil(wb, output);
	}
	
	/**
	 * Creates a new converter to HTML for the given workbook. If the path ends
	 * with "<tt>.xlsx</tt>" an {@link XSSFWorkbook} will be used; otherwise
	 * this will use an {@link HSSFWorkbook}.
	 * 
	 * @param path
	 *        The file that has the workbook.
	 * @param output
	 *        Where the HTML output will be written.
	 * 
	 * @return An object for converting the workbook to HTML.
	 */
	public static Excel2HtmlUtil create(String path, Appendable output) throws IOException
	{
		return create(new FileInputStream(path), output);
	}
	
	/**
	 * Creates a new converter to HTML for the given workbook. This attempts to
	 * detect whether the input is XML (so it should create an
	 * {@link XSSFWorkbook} or not (so it should create an {@link HSSFWorkbook}
	 * ).
	 * 
	 * @param in
	 *        The input stream that has the workbook.
	 * @param output
	 *        Where the HTML output will be written.
	 * 
	 * @return An object for converting the workbook to HTML.
	 */
	public static Excel2HtmlUtil create(InputStream in, Appendable output) throws IOException
	{
		try
		{
			Workbook wb = WorkbookFactory.create(in);
			return create(wb, output);
		}
		catch (InvalidFormatException e)
		{
			throw new IllegalArgumentException("Cannot create workbook from stream", e);
		}
	}
	
	private Excel2HtmlUtil(Workbook wb, Appendable output)
	{
		if (wb == null)
			throw new NullPointerException("wb");
		if (output == null)
			throw new NullPointerException("output");
		this.wb = wb;
		this.output = output;
		setupColorMap();
	}
	
	private void setupColorMap()
	{
		if (wb instanceof HSSFWorkbook)
			helper = new HSSFHtmlHelper((HSSFWorkbook) wb);
		else if (wb instanceof XSSFWorkbook)
			helper = new XSSFHtmlHelper((XSSFWorkbook) wb);
		else
			throw new IllegalArgumentException("unknown workbook type: " + wb.getClass().getSimpleName());
	}
	
	public void setCompleteHTML(boolean completeHTML)
	{
		this.completeHTML = completeHTML;
	}
	
	public void printPage() throws IOException
	{
		try
		{
			ensureOut();
			if (completeHTML)
			{
				// out.format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>%n");
				// out.format("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>%n");
				out.format("<html>%n");
				out.format("<head>%n");
				// out.format("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />%n");
				out.format("</head>%n");
				out.format("<body>%n");
			}
			
			print();
			
			if (completeHTML)
			{
				out.format("</body>%n");
				out.format("</html>%n");
			}
		}
		finally
		{
			if (out != null)
				out.close();
			if (output instanceof Closeable)
			{
				Closeable closeable = (Closeable) output;
				closeable.close();
			}
		}
	}
	
	public void print()
	{
		printInlineStyle();
		printSheets();
	}
	
	private void printInlineStyle()
	{
		// out.format("<link href=\"excelStyle.css\" rel=\"stylesheet\" type=\"text/css\">%n");
		out.format("<style type=\"text/css\">%n");
		printStyles();
		out.format("</style>%n");
	}
	
	private void ensureOut()
	{
		if (out == null)
			out = new Formatter(output);
	}
	
	public void printStyles()
	{
		ensureOut();
		
		// First, copy the base css
		BufferedReader in = null;
		try
		{
			in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("excelStyle.css")));
			String line;
			while ((line = in.readLine()) != null)
			{
				out.format("%s%n", line);
			}
		}
		catch (IOException e)
		{
			throw new IllegalStateException("Reading standard css", e);
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					// noinspection ThrowFromFinallyBlock
					throw new IllegalStateException("Reading standard css", e);
				}
			}
		}
		
		// now add css for each used style
		Set<CellStyle> seen = new HashSet<CellStyle>();
		for (int i = 0; i < wb.getNumberOfSheets(); i++)
		{
			Sheet sheet = wb.getSheetAt(i);
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext())
			{
				Row row = rows.next();
				for (Cell cell : row)
				{
					CellStyle style = cell.getCellStyle();
					if (!seen.contains(style))
					{
						printStyle(style);
						seen.add(style);
					}
				}
			}
		}
	}
	
	private void printStyle(CellStyle style)
	{
		out.format(".%s .%s {%n", DEFAULTS_CLASS, styleName(style));
		styleContents(style);
		out.format("}%n");
	}
	
	private void styleContents(CellStyle style)
	{
		styleOut("text-align", style.getAlignment(), ALIGN);
		styleOut("vertical-align", style.getAlignment(), VERTICAL_ALIGN);
		fontStyle(style);
		borderStyles(style);
		helper.colorStyles(style, out);
	}
	
	private void borderStyles(CellStyle style)
	{
		styleOut("border-left", style.getBorderLeft(), BORDER);
		styleOut("border-right", style.getBorderRight(), BORDER);
		styleOut("border-top", style.getBorderTop(), BORDER);
		styleOut("border-bottom", style.getBorderBottom(), BORDER);
	}
	
	private void fontStyle(CellStyle style)
	{
		Font font = wb.getFontAt(style.getFontIndex());
		
		if (font.getBoldweight() >= HSSFFont.BOLDWEIGHT_NORMAL)
			out.format("  font-weight: bold;%n");
		if (font.getItalic())
			out.format("  font-style: italic;%n");
		
		int fontheight = font.getFontHeightInPoints();
		if (fontheight == 9)
		{
			// fix for stupid ol Windows
			fontheight = 10;
		}
		out.format("  font-size: %dpt;%n", fontheight);
		
		// Font color is handled with the other colors
	}
	
	private String styleName(CellStyle style)
	{
		if (style == null)
			style = wb.getCellStyleAt((short) 0);
		StringBuilder sb = new StringBuilder();
		Formatter fmt = new Formatter(sb);
		fmt.format("style_%02x", style.getIndex());
		return fmt.toString();
	}
	
	private <K> void styleOut(String attr, K key, Map<K, String> mapping)
	{
		String value = mapping.get(key);
		if (value != null)
		{
			out.format("  %s: %s;%n", attr, value);
		}
	}
	
	private static int ultimateCellType(Cell c)
	{
		int type = c.getCellType();
		if (type == Cell.CELL_TYPE_FORMULA)
			type = c.getCachedFormulaResultType();
		return type;
	}
	
	private void printSheets()
	{
		ensureOut();
		Sheet sheet = wb.getSheetAt(0);
		printSheet(sheet);
	}
	
	public void printSheet(Sheet sheet)
	{
		ensureOut();
		out.format("<table class=%s>%n", DEFAULTS_CLASS);
		printCols(sheet);
		printSheetContent(sheet);
		out.format("</table>%n");
	}
	
	private void printCols(Sheet sheet)
	{
		out.format("<col/>%n");
		ensureColumnBounds(sheet);
		for (int i = firstColumn; i < endColumn; i++)
		{
			out.format("<col/>%n");
		}
	}
	
	private void ensureColumnBounds(Sheet sheet)
	{
		if (gotBounds)
			return;
		
		Iterator<Row> iter = sheet.rowIterator();
		firstColumn = (iter.hasNext() ? Integer.MAX_VALUE : 0);
		endColumn = 0;
		while (iter.hasNext())
		{
			Row row = iter.next();
			short firstCell = row.getFirstCellNum();
			if (firstCell >= 0)
			{
				firstColumn = Math.min(firstColumn, firstCell);
				endColumn = Math.max(endColumn, row.getLastCellNum());
			}
		}
		gotBounds = true;
	}
	
	private void printColumnHeads()
	{
		out.format("<thead>%n");
		out.format("  <tr class=%s>%n", COL_HEAD_CLASS);
		out.format("    <th class=%s>&#x25CA;</th>%n", COL_HEAD_CLASS);
		// noinspection UnusedDeclaration
		StringBuilder colName = new StringBuilder();
		for (int i = firstColumn; i < endColumn; i++)
		{
			colName.setLength(0);
			int cnum = i;
			do
			{
				colName.insert(0, (char) ('A' + cnum % 26));
				cnum /= 26;
			} while (cnum > 0);
			out.format("    <th class=%s>%s</th>%n", COL_HEAD_CLASS, colName);
		}
		out.format("  </tr>%n");
		out.format("</thead>%n");
	}
	
	private void printSheetContent(Sheet sheet)
	{
		// printColumnHeads();
		
		out.format("<tbody>%n");
		Iterator<Row> rows = sheet.rowIterator();
		while (rows.hasNext())
		{
			Row row = rows.next();
			
			out.format("  <tr>%n");
			// out.format("  <td class=%s>%d</td>%n", ROW_HEAD_CLASS,
			// row.getRowNum() + 1);
			for (int i = firstColumn; i < endColumn; i++)
			{
				String content = "&nbsp;";
				String attrs = "";
				CellStyle style = null;
				if (i >= row.getFirstCellNum() && i < row.getLastCellNum())
				{
					Cell cell = row.getCell(i);
					if (cell != null)
					{
						style = cell.getCellStyle();
						attrs = tagStyle(cell, style);
						// Set the value that is rendered for the cell
						// also applies the format
						CellFormat cf = CellFormat.getInstance(style.getDataFormatString());
						CellFormatResult result = cf.apply(cell);
						content = result.text;
						if (content.equals(""))
							content = "&nbsp;";
					}
				}
				out.format("    <td class=%s %s>%s</td>%n", styleName(style), attrs, content);
			}
			out.format("  </tr>%n");
		}
		out.format("</tbody>%n");
	}
	
	private String tagStyle(Cell cell, CellStyle style)
	{
		if (null != style && style.getAlignment() == ALIGN_GENERAL)
		{
			switch (ultimateCellType(cell))
			{
				case HSSFCell.CELL_TYPE_STRING:
					return "style=\"text-align: left;\"";
				case HSSFCell.CELL_TYPE_BOOLEAN:
				case HSSFCell.CELL_TYPE_ERROR:
					return "style=\"text-align: center;\"";
				case HSSFCell.CELL_TYPE_NUMERIC:
				default:
					// "right" is the default
					break;
			}
		}
		return "";
	}
	
	/**
	 * 读取整个页面内容
	 * <p>
	 * 
	 * @param htmlurl
	 * @return HTML页面内容
	 * @throws IOException
	 */
	public String getOneHtml(final String htmlurl) throws IOException
	{
		String temp;
		final StringBuffer buffer = new StringBuffer();
		try
		{
			// // 读取网页全部内容
			final BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(htmlurl),
					DEFAULT_UTF_8));
			while ((temp = in.readLine()) != null)
			{
				// //System.out.println(temp);
				// System.out.println(new String(temp.getBytes("ISO-8859-1"),
				// DEFAULT_UTF_8));
				buffer.append(temp);
			}
			
			in.close();
		}
		catch (final MalformedURLException me)
		{
			// System.out.println("你输入的URL格式有问题！请仔细输入");
			me.printStackTrace();
			throw me;
		}
		catch (final IOException e)
		{
			e.printStackTrace();
			throw e;
		}
		return buffer.toString();
	}
	
	public synchronized static String excel07ToHtml(String path) throws IOException
	{
		String content = "";
		String htmlPath = DEFAULT_EXCEL_TO_HTML;
		String fileName = (new Date().getTime()) + HTML_SUFFIX;
		if (ExcelFileUtils.createDir(htmlPath))
		{
			File file = new File(htmlPath + fileName);
			if (file.exists())
			{
				file.createNewFile();
			}
			
			Excel2HtmlUtil toHtml = create(path, new PrintWriter(file, DEFAULT_UTF_8));
			toHtml.setCompleteHTML(true);
			toHtml.printPage();
			content = toHtml.getOneHtml(htmlPath + fileName);
			
			ExcelFileUtils.deleteFile(file);
		}
		
		return content;
	}
	
	/**
	 * Run this class as a program
	 * 
	 * @param args
	 *        The command line arguments.
	 * 
	 * @throws Exception
	 *         Exception we don't recover from.
	 */
	public static void main(String[] args) throws Exception
	{
		PrintWriter printWriter = new PrintWriter(new FileWriter("C:\\2.html"));
		Excel2HtmlUtil toHtml = create("C:\\1.xls", printWriter);
		toHtml.setCompleteHTML(true);
		toHtml.printPage();
		
	}
}
