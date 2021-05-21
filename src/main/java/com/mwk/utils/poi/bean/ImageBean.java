
package com.mwk.utils.poi.bean;

/** 
* @Description POI处理的图片
* @author Minweikai
* @date 2016年9月9日 上午8:16:08  
*/
public class ImageBean extends ContentBean{
	
	private int width;
	private int height;
	private String type;
	private byte[] content;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
