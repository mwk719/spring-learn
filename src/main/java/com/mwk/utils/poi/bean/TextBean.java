
package com.mwk.utils.poi.bean;

/** 
* @Description POI处理的文字
* @author Minweikai
* @date 2016年9月9日 上午8:15:55  
*/
public class TextBean extends ContentBean{
	
	private String content;
	private FontStyle fontStyle;

	public FontStyle getFontStyle() {
		return fontStyle;
	}

	/**
	 * @param content
	 * @param fontStyle
	 */
	public TextBean(String content, FontStyle fontStyle) {
		super();
		this.content = content;
		this.fontStyle = fontStyle;
	}

	/**
	 * @param content
	 */
	public TextBean(String content) {
		super();
		this.content = content;
	}

	public void setFontStyle(FontStyle fontStyle) {
		this.fontStyle = fontStyle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
