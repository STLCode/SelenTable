package com.softtechlabs.selenquery.plugins;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface SelenTable {
	
	public abstract int getRowCount();
	
	public abstract int getColumnCount();

	public abstract WebElement getRowObject(int rowindex);

	public abstract WebElement getRowObject(String colHeaderText, String uniqueValue);

	public abstract int getRowIndex(String colHeaderText, String uniqueValue);
	
	public abstract int getColIndexfromDisplayHeader(String colHeaderText);

	public abstract WebElement getCellObject(int rowindex, int colIndex);

	public abstract WebElement getCellObject(int rowindex, String colHeaderText);

	public abstract WebElement getControlInsideCell(int rowindex, String colHeaderText, By innerControlSelector);

	public abstract String getCellValue(int rowindex, int colIndex);

	public abstract String getCellValue(int rowindex, String colHeaderText);

	public abstract void rowClick(int rowindex);

	public abstract void rowDoubleClick(int rowindex);

	public abstract void rowRightClick(int rowindex);

	public abstract void rowHover(int rowindex);

	public abstract List<String> getColumnNames();

	public abstract HashMap<String, String> getRow(int rowindex);

	public abstract boolean isRowExist(HashMap<String, String> row);

	

}
