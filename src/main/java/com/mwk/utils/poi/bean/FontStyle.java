package com.mwk.utils.poi.bean;

/**
 * @Description POI处理的字体样式
 * @author Minweikai
 * @date 2016年9月8日 下午5:52:17
 */
public class FontStyle {
	
	private String fontFamily = "宋体";
	private String colorVal = "000000";
	private String fontSize = "26";
	private boolean isBlod = false;
	private boolean isItalic = false;
	private boolean isStrike = false;
	private boolean isUnderLine = false;
	private int underLineStyle = 0;
	private String underLineColor = null;
	private boolean isShd = false;
	private int shdValue = 0;
	private String shdColor = null;
	
	public String getFontFamily() {
		return fontFamily;
	}
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}
	public String getColorVal() {
		return colorVal;
	}
	public void setColorVal(String colorVal) {
		this.colorVal = colorVal;
	}
	public String getFontSize() {
		return fontSize;
	}
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
	public boolean isBlod() {
		return isBlod;
	}
	public void setBlod(boolean isBlod) {
		this.isBlod = isBlod;
	}
	public boolean isItalic() {
		return isItalic;
	}
	public void setItalic(boolean isItalic) {
		this.isItalic = isItalic;
	}
	public boolean isStrike() {
		return isStrike;
	}
	public void setStrike(boolean isStrike) {
		this.isStrike = isStrike;
	}
	public boolean isUnderLine() {
		return isUnderLine;
	}
	public void setUnderLine(boolean isUnderLine) {
		this.isUnderLine = isUnderLine;
	}
	public int getUnderLineStyle() {
		return underLineStyle;
	}
	public void setUnderLineStyle(int underLineStyle) {
		this.underLineStyle = underLineStyle;
	}
	public String getUnderLineColor() {
		return underLineColor;
	}
	public void setUnderLineColor(String underLineColor) {
		this.underLineColor = underLineColor;
	}
	public boolean isShd() {
		return isShd;
	}
	public void setShd(boolean isShd) {
		this.isShd = isShd;
	}
	public int getShdValue() {
		return shdValue;
	}
	public void setShdValue(int shdValue) {
		this.shdValue = shdValue;
	}
	public String getShdColor() {
		return shdColor;
	}
	public void setShdColor(String shdColor) {
		this.shdColor = shdColor;
	}
}
