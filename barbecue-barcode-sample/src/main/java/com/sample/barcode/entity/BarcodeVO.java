package com.sample.barcode.entity;

import java.awt.Font;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public class BarcodeVO {
	private String code;
	private boolean visible = true;
	private int width = 800;
	private int height = 300;
	private BarcodeType type = BarcodeType.CODE128;
	private Font font = new Font("Serif", Font.PLAIN, 15);
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public BarcodeType getType() {
		return type;
	}
	public void setType(BarcodeType type) {
		this.type = type;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public BarcodeVO() {}
	
	public BarcodeVO(String code, BarcodeType type) {
		this.code = code;
		this.type = Optional.ofNullable(type)
				.orElse(StringUtils.isNumeric(code) ? BarcodeType.CODE39 : BarcodeType.CODE128);
	}
	
	public BarcodeVO(String code, BarcodeType type, boolean visible) {
		this.code = code;
		this.type = type;
		this.visible = visible;
	}
}
