
package com.mwk.utils.poi;

import com.mwk.utils.poi.bean.FontStyle;
import com.mwk.utils.poi.bean.ImageBean;
import com.mwk.utils.poi.bean.TextBean;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description word处理工具类
 * @author Minweikai
 * @date 2016年8月30日 下午4:40:46
 */
public class WordDocUtils {

//	private static SimpleDateFormat sdf_default = new SimpleDateFormat(SystemConstants.DATE_FORMATE);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private final static Logger log = LoggerFactory.getLogger(WordDocUtils.class);
    /**
	 * 文子描述
	 */
	private static String picAttch = "";

	public static void setPicAttch(String picAttch) {
		WordDocUtils.picAttch = picAttch;
	}

	public static HWPFDocument testWriteDoc(InputStream is, Map<String, Object> params) throws Exception {
		HWPFDocument doc = new HWPFDocument(is);
		Range range = doc.getRange();
		// 把range范围内的${reportDate}替换为当前的日期
		for (String key : params.keySet()) {
			Object value = params.get(key);
			if (value instanceof Date) {
				range.replaceText("${" + key + "}", sdf.format(value));
			} else {
				range.replaceText("${" + key + "}", params.get(key).toString());
			}
		}
		return doc;
	}

	
	public static XWPFDocument testWriteDocx(InputStream is, Map<String, Object> params) throws Exception {

		CustomXWPFDocument doc = new CustomXWPFDocument(is);
		// XWPFDocument doc = new XWPFDocument(is);

		// 替换段落里面的变量
		replaceInPara(doc, params);
		// 替换表格里面的变量
		replaceInTable(doc, params);

		// for (Entry<String, Object> entry : params.entrySet()) {
		// Object value = entry.getValue();
		// if(value instanceof Map) {
		//
		// }
		// }
		// Iterator<XWPFParagraph> paragraphsIterator =
		// doc.getParagraphsIterator();
		// XWPFParagraph paragraph = paragraphsIterator.next();

		// Map<String,Object> pic = (Map<String, Object>) params.get("pic001");
		// byte[] byteArray = (byte[]) pic.get("content");
		// ByteArrayInputStream byteInputStream = new
		// ByteArrayInputStream(byteArray);

		// String ind = doc.addPictureData(byteInputStream,
		// CustomXWPFDocument.PICTURE_TYPE_PNG);
		// System.out.println(ind);
		// doc.createPicture(0, 190, 226, paragraph);

		return doc;
	}

	/**
	 * 替换段落里面的变量
	 * 
	 * @param doc
	 *            要替换的文档
	 * @param params
	 *            参数
	 */
	private static void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
		Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
		XWPFParagraph para;
		while (iterator.hasNext()) {
			para = iterator.next();
			replaceInPara(doc, para, params);
		}
	}

	/**
	 * 替换段落里面的变量
	 * 
	 * @param para
	 *            要替换的段落
	 * @param params
	 *            参数
	 */
	@SuppressWarnings("unchecked")
	private static void replaceInPara(XWPFDocument doc, XWPFParagraph para, Map<String, Object> params) {
		List<XWPFRun> runs;
		Matcher matcher;
		if (matcher(para.getParagraphText()).find()) {
			runs = para.getRuns();
			for (int i = 0; i < runs.size(); i++) {
				XWPFRun run = runs.get(i);
				String runText = run.toString();
				matcher = matcher(runText);
				if (matcher.find()) {
					while ((matcher = matcher(runText)).find()) {

						// 清空占位符
						runText = "";
						para.removeRun(i);
						para.insertNewRun(i).setText(runText);
						
						Object value = params.get(matcher.group(1));

						value = value != null ? value : "";

						// 如果是String则转为处理文本
						if (value instanceof String) {
							runText = matcher.replaceFirst(String.valueOf(value));
							insertText(para, i, runText);
						}
						// 如果是TextBean则转为处理文本
						else if (value instanceof TextBean) {
							TextBean textBean = (TextBean) value;
							insertText(para, i, textBean.getContent(), textBean.getFontStyle());
						}
						// 如果是ImageBean则转为处理图片
						else if (value instanceof ImageBean) {
							insertPic(doc, para, (ImageBean) value, i);
						}
						// 如果是List则转为处理图文混排的复杂列表
						else if (value instanceof List) {
							insertComplexList(doc, para, (List<Object>) value, i);
						}

						// if (value instanceof String) {
						// //
						// 直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
						// // 所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
						// para.removeRun(i);
						//
						// insertText(para, i, runText);
						// }

						// 如果是map则转为处理图片
						// if(value instanceof Map) {
						// runText = "";
						// para.removeRun(i);
						// para.insertNewRun(i).setText(runText);
						// ((CustomXWPFDocument) doc).createPicture(0, 190, 226,
						// para);
						// }
					}
				}
			}
		}
	}

	/**
	 * @Description: 插入图文混排的复杂列表
	 * @author Minweikai
	 * @date 2016年9月8日 下午1:43:51
	 * @param doc
	 * @param para
	 * @param values
	 * @param runPos
	 */
	@SuppressWarnings("unchecked")
	private static void insertComplexList(XWPFDocument doc, XWPFParagraph para, List<Object> values, int runPos) {
//		System.out.println("lst.size()  = " + values.size());

		// 是否需要换行，因为连续几张图片渲染的时候不需要换行，
		// 而连续的文本渲染的时候需要换行
		Boolean forceNewLine = true;

		if (!CollectionUtils.isEmpty(values)) {
			int curentIndex = values.size() - 1;
			for (int j = curentIndex; j >= 0; j--) {

				// 增加每个条目之间的换行
				// if(j < curentIndex) {
				if (j < curentIndex && forceNewLine) {
					log.debug("添加换行 insertComplexList " + values.get(j).toString());
					para.insertNewRun(runPos).addBreak();
				}

				Object lstValue = values.get(j);

				log.debug("write to doc : " + lstValue);

				forceNewLine = true;
				// 插入文本
				if (lstValue instanceof String) {
					insertText(para, runPos, lstValue.toString());
				}
				else if (lstValue instanceof TextBean) {
					TextBean textBean = (TextBean) lstValue;
					insertText(para, runPos, textBean.getContent(), textBean.getFontStyle());
				}
				// 插入图片
				else if (lstValue instanceof ImageBean) {
					insertPic(doc, para, (ImageBean) lstValue, runPos);
					forceNewLine = false;
				}
				// 插入图文混排的复杂列表
				else if (lstValue instanceof List) {
					para.insertNewRun(runPos).addBreak();
					insertComplexList(doc, para, (List<Object>) lstValue, runPos);
				}
			}
		}
	}

	/**
	 * @Description: 插入图片到段落最后
	 * @author Minweikai
	 * @date 2016年9月8日 上午11:24:38
	 * @param doc
	 * @param para
	 * @param lstValue
	 */
	@SuppressWarnings("unused")
	private static void insertPic(XWPFDocument doc, XWPFParagraph para, ImageBean imageBean) {
		insertPic(doc, para, imageBean, -1);
	}

	/**
	 * @Description: 插入图片到段落指定位置
	 * @author Minweikai
	 * @date 2016年9月8日 上午11:38:19
	 * @param doc
	 * @param para
	 * @param imageBean
	 * @param runPos
	 */
	private static void insertPic(XWPFDocument doc, XWPFParagraph para, ImageBean imageBean, int runPos) {

		try {
			byte[] byteArray = imageBean.getContent();
			ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);

			String type = imageBean.getType();
			Integer width = imageBean.getWidth();
			Integer height = imageBean.getHeight();

			// System.out.println("bf doc.getAllPictures().size() = " +
			// doc.getAllPictures().size());
			// 插入图片
			String relationId = doc.addPictureData(byteInputStream, getPictureType(type));

			// System.out.println("af doc.getAllPictures().size() = " +
			// doc.getAllPictures().size());
			((CustomXWPFDocument) doc).createPicture(para, relationId, width, height, picAttch, runPos, false);

		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
	}
	
//	private static void setDefaultStyle(XWPFRun pRun) {
//		setParagraphFontInfoAndUnderLineStyle(pRun, new FontStyle());
//	}
	
	private static void setFontStyle(XWPFRun pRun, FontStyle fs) {
		if(fs == null) {
			fs = new FontStyle();
		}
		setParagraphFontInfoAndUnderLineStyle(pRun, fs);
	}

	/**
	 * @Description: 插入文本到当前段落
	 * @author Minweikai
	 * @date 2016年9月8日 上午9:50:15
	 * @param para
	 *            段落
	 * @param i
	 *            段落下标
	 * @param runText
	 *            插入的文本
	 */
	private static void insertText(XWPFParagraph para, int i, String runText, FontStyle fs) {

		String[] contents = runText.split("\n");
		int curIndex = contents.length - 1;

		XWPFRun runFirst = para.insertNewRun(i);
		
		setFontStyle(runFirst, fs);
		
		runFirst.setText(contents[curIndex]);
		// 倒序循环输出
		for (int j = curIndex - 1; j >= 0; j--) {
			para.insertNewRun(i).addBreak();
			// para.insertNewRun(i).setText(contents[j]);
			
			XWPFRun run = para.insertNewRun(i);

			setFontStyle(run, fs);

			run.setText(contents[j]);
		}
	}
	
	/**
	 * @Description: 插入文本到当前段落
	 * @author Minweikai
	 * @date 2016年9月8日 上午9:50:15
	 * @param para
	 *            段落
	 * @param i
	 *            段落下标
	 * @param runText
	 *            插入的文本
	 */
	private static void insertText(XWPFParagraph para, int i, String runText) {
		insertText(para, i, runText, null);
//		String[] contents = runText.split("\n");
//		int curIndex = contents.length - 1;
//
//		XWPFRun runFirst = para.insertNewRun(i);
//		setDefaultStyle(runFirst);
//		
//		runFirst.setText(contents[curIndex]);
//		// 倒序循环输出
//		for (int j = curIndex - 1; j >= 0; j--) {
//			para.insertNewRun(i).addBreak();
//			// para.insertNewRun(i).setText(contents[j]);
//			
//			XWPFRun run = para.insertNewRun(i);
//
//			setDefaultStyle(run);
//
//			run.setText(contents[j]);
//		}
	}

	/**
	 * 根据图片类型，取得对应的图片类型代码
	 * 
	 * @param picType
	 * @return int
	 */
	private static int getPictureType(String picType) {
		int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
		if (picType != null) {
			if (picType.equalsIgnoreCase("png")) {
				res = CustomXWPFDocument.PICTURE_TYPE_PNG;
			} else if (picType.equalsIgnoreCase("dib")) {
				res = CustomXWPFDocument.PICTURE_TYPE_DIB;
			} else if (picType.equalsIgnoreCase("emf")) {
				res = CustomXWPFDocument.PICTURE_TYPE_EMF;
			} else if (picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")) {
				res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
			} else if (picType.equalsIgnoreCase("wmf")) {
				res = CustomXWPFDocument.PICTURE_TYPE_WMF;
			}
		}
		return res;
	}

	/**
	 * 替换段落里面的变量
	 * 
	 * @param para
	 *            要替换的段落
	 * @param params
	 *            参数
	 */
//	private static void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
//		List<XWPFRun> runs;
//		Matcher matcher;
//		if (matcher(para.getParagraphText()).find()) {
//			runs = para.getRuns();
//			for (int i = 0; i < runs.size(); i++) {
//				XWPFRun run = runs.get(i);
//				String runText = run.toString();
//				matcher = matcher(runText);
//				if (matcher.find()) {
//
//					// 直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
//					// 所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
//					para.removeRun(i);
//					para.insertNewRun(i).setText("");
//
//					while ((matcher = matcher(runText)).find()) {
//						Object value = params.get(matcher.group(1));
//						value = value == null ? "" : value;
//						if (value instanceof String) {
//							runText = matcher.replaceFirst(String.valueOf(value));
//							// 直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
//							// // 所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
//							para.removeRun(i);
//							para.insertNewRun(i).setText(runText);
//							break;
//						}
//					}
//
//				}
//			}
//		}
//	}

	/**
	 * 替换表格里面的变量
	 * 
	 * @param doc
	 *            要替换的文档
	 * @param params
	 *            参数
	 */
	private static void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
		Iterator<XWPFTable> iterator = doc.getTablesIterator();
		XWPFTable table;
		List<XWPFTableRow> rows;
		List<XWPFTableCell> cells;
		List<XWPFParagraph> paras;
		while (iterator.hasNext()) {
			table = iterator.next();
			rows = table.getRows();
			for (XWPFTableRow row : rows) {
				cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					paras = cell.getParagraphs();
					for (XWPFParagraph para : paras) {
						replaceInPara(doc, para, params);
					}
				}
			}
		}
	}

	/**
	 * 正则匹配字符串
	 * 
	 * @param str
	 * @return
	 */
	private static Matcher matcher(String str) {
		Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}

	/**
	 * 将输入流中的数据写入字节数组
	 * 
	 * @param in
	 * @return
	 */
	public static byte[] inputStream2ByteArray(InputStream in, boolean isClose) {
		byte[] byteArray = null;
		try {
			int total = in.available();
			byteArray = new byte[total];
			in.read(byteArray);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (isClose) {
				try {
					in.close();
				} catch (Exception e2) {
					log.error("关闭流失败");
				}
			}
		}
		return byteArray;
	}

//	public static void setParagraphFontInfoAndUnderLineStyle(XWPFRun pRun, String fontFamily,
//			String colorVal, String fontSize, boolean isBlod, boolean isItalic, boolean isStrike, boolean isUnderLine,
//			int underLineStyle, String underLineColor, boolean isShd, int shdValue, String shdColor) {
	public static void setParagraphFontInfoAndUnderLineStyle(XWPFRun pRun, FontStyle fs) {
		String fontFamily = fs.getFontFamily();
		String colorVal = fs.getColorVal();
		String fontSize = fs.getFontSize();
		boolean isBlod = fs.isBlod();
		boolean isItalic = fs.isItalic();
		boolean isStrike = fs.isStrike();
		boolean isUnderLine = fs.isUnderLine();
		int underLineStyle = fs.getUnderLineStyle();
		String underLineColor = fs.getUnderLineColor();
		boolean isShd = fs.isShd();
		int shdValue = fs.getShdValue();
		String shdColor = fs.getShdColor();
		
//		XWPFRun pRun = null;
//		if (p.getRuns() != null && p.getRuns().size() > 0) {
//			pRun = p.getRuns().get(0);
//		} else {
//			pRun = p.createRun();
//		}
//		pRun.setText(content);

		CTRPr pRpr = null;
		if (pRun.getCTR() != null) {
			pRpr = pRun.getCTR().getRPr();
			if (pRpr == null) {
				pRpr = pRun.getCTR().addNewRPr();
			}
		}

		// 设置字体
		CTFonts fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr.addNewRFonts();
		fonts.setAscii(fontFamily);
		fonts.setEastAsia(fontFamily);
		fonts.setHAnsi(fontFamily);

		// 设置字体大小
		CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
		sz.setVal(new BigInteger(fontSize));

		CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs();
		szCs.setVal(new BigInteger(fontSize));

		// 设置字体样式
		if (isBlod) {
			pRun.setBold(isBlod);
		}
		if (isItalic) {
			pRun.setItalic(isItalic);
		}
		if (isStrike) {
			pRun.setStrike(isStrike);
		}
		if (colorVal != null) {
			pRun.setColor(colorVal);
		}

		// 设置字突出显示文本
		if (underLineStyle > 0 && underLineStyle < 17) {
//			CTHighlight hightLight = pRpr.isSetHighlight() ? pRpr.getHighlight() : pRpr.addNewHighlight();
//			hightLight.setVal(STHighlightColor.Enum.forInt(underLineStyle));
		}

		// 设置下划线样式
		if (isUnderLine) {
			CTUnderline u = pRpr.isSetU() ? pRpr.getU() : pRpr.addNewU();
			u.setVal(STUnderline.Enum.forInt(Math.abs(underLineStyle % 19)));
			if (underLineColor != null) {
				u.setColor(underLineColor);
			}
		}

		if (isShd) {
			// 设置底纹
			CTShd shd = pRpr.isSetShd() ? pRpr.getShd() : pRpr.addNewShd();
			if (shdValue > 0 && shdValue <= 38) {
				shd.setVal(STShd.Enum.forInt(underLineStyle));
			}
			if (shdColor != null) {
				shd.setColor(shdColor);
			}
		}
	}

	// 设置页面背景色
	public void setDocumentbackground(XWPFDocument document, String bgColor) {
//		CTBackground bg = document.getDocument().isSetBackground() ? document.getDocument().getBackground()
//				: document.getDocument().addNewBackground();
//		bg.setColor(bgColor);
	}
}
