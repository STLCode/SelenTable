package com.softtechlabs.selenquery.plugins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class SelenWebTable implements SelenTable {

	WebElement tableHeader = null;
	WebElement tableContents = null;
	WebDriver driver = null;
	String xpath = null;
	int tries = 0;

	public SelenWebTable(WebDriver driver, String xpath) {
		this.driver = driver;
		this.xpath = xpath;
		rebuildSelenTable();
	}

	private void rebuildSelenTable() {
		List<WebElement> ctrls = driver.findElements(By.xpath(xpath));
		// in some of the application (gwt-table) they
		// have 2 element one for header and another
		// for Tbody
		if (ctrls.size() == 2) { 
			tableHeader = ctrls.get(0);
			tableContents = ctrls.get(1);
		} else if (ctrls.size() == 1) {
			tableHeader = ctrls.get(0);
			tableContents = ctrls.get(0);
		}
	}


	@Override
	public int getRowCount() {
		try {
			if (tableContents == null) {
				return 0;
			} else {
				return tableContents.findElements(By.tagName("tr")).size();
			}
		} catch (StaleElementReferenceException e) {
			rebuildSelenTable();
			return getRowCount();
		}

	}


	@Override
	public int getColumnCount() {
		try {
			int colcount = tableHeader.findElements(By.tagName("th")).size();
			if (colcount == 0) {
				colcount = tableHeader.findElements(By.tagName("td")).size();
			}
			return colcount;
		} catch (StaleElementReferenceException e) {
			rebuildSelenTable();
			return getRowCount();
		}
	}


	@Override
	public WebElement getRowObject(int rowindex) {
		try {
			List<WebElement> rows = tableContents.findElements(By.tagName("tr"));
			if (rows.size() > rowindex) {
				return rows.get(rowindex);
			} else {
				return null;
			}
		} catch (StaleElementReferenceException e) {
			rebuildSelenTable();
			return getRowObject(rowindex);
		}
	}


	@Override
	public WebElement getRowObject(String colHeaderText, String uniqueValue) {
		int colIndex = getColIndexfromDisplayHeader(colHeaderText);
		for (int i = 0; i < getRowCount(); i++) {
			if (uniqueValue.equals(getCellValue(i, colIndex))) {
				return getRowObject(i);
			}
		}
		return null;
	}


	@Override
	public int getRowIndex(String colHeaderText, String uniqueValue) {
		int colIndex = getColIndexfromDisplayHeader(colHeaderText);
		for (int i = 0; i < getRowCount(); i++) {
			if (uniqueValue.equals(getCellValue(i, colIndex))) {
				return i;
			}
		}
		return -1;
	}


	@Override
	public int getColIndexfromDisplayHeader(String colHeaderText) {
		try {
			List<WebElement> cols = tableHeader.findElements(By.tagName("th"));
			int i = 0;
			boolean colfound = false;
			for (WebElement col : cols) {
				if (colHeaderText.equals(col.getAttribute("textContent"))) {
					colfound = true;
					break;
				} else {
					i++;
				}
			}
			if (colfound) {
				return i;
			} else {
				return -1;
			}
		} catch (StaleElementReferenceException e) {
			rebuildSelenTable();
			return getColIndexfromDisplayHeader(colHeaderText);
		}
	}


	@Override
	public WebElement getCellObject(int rowindex, int colIndex) {
		try {
			WebElement row = getRowObject(rowindex);
			if (row != null) {
				int colCount = getColumnCount();
				if (colCount > colIndex) {
					return row.findElements(By.tagName("td")).get(colIndex);
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (StaleElementReferenceException e) {
			rebuildSelenTable();
			return getCellObject(rowindex, colIndex);
		}
	}


	@Override
	public WebElement getCellObject(int rowindex, String colHeaderText) {
		try {
			int colIndex = getColIndexfromDisplayHeader(colHeaderText);
			if (colIndex >= 0) {
				return getCellObject(rowindex, colIndex);
			} else {
				return null;
			}
		} catch (StaleElementReferenceException e) {
			rebuildSelenTable();
			return getCellObject(rowindex, colHeaderText);
		}
	}


	@Override
	public WebElement getControlInsideCell(int rowindex, String colHeaderText, By innerControlSelector) {
		// error here
		WebElement cellObject = getCellObject(rowindex, colHeaderText);
		List<WebElement> elements = cellObject.findElements(innerControlSelector);
		if (elements.size() > 0) {
			WebElement control = elements.get(0);
			return control;
		}
		return null;
	}


	@Override
	public String getCellValue(int rowindex, int colIndex) {
		try {
			WebElement cell = getCellObject(rowindex, colIndex);
			if (cell != null) {
				return cell.getAttribute("textContent");
			}
			System.out.println("Error:CouldNotFoundCellObject");
			return "Error:CouldNotFoundCellObject";
		} catch (StaleElementReferenceException e) {
			rebuildSelenTable();
			return getCellValue(rowindex, colIndex);
		}
	}


	@Override
	public String getCellValue(int rowindex, String colHeaderText) {
		try {
			WebElement cell = getCellObject(rowindex, colHeaderText);
			if (cell != null) {
				return cell.getAttribute("textContent");
			}
			System.out.println("Error:CouldNotFoundCellObject");
			return "Error:CouldNotFoundCellObject";
		} catch (StaleElementReferenceException e) {
			rebuildSelenTable();
			return getCellValue(rowindex, colHeaderText);
		}
	}


	@Override
	public void rowClick(int rowindex) {
		try {
			getRowObject(rowindex).click();
		} catch (StaleElementReferenceException e) {
			rebuildSelenTable();
			rowClick(rowindex);
		}
	}

	@Override
	public void rowDoubleClick(int rowindex) {
		try {
			Actions action = new Actions(driver);
			WebElement elm = getRowObject(rowindex).findElements(By.tagName("td")).get(0);
			action.doubleClick(elm).perform();
		} catch (StaleElementReferenceException e) {
			rebuildSelenTable();
			rowDoubleClick(rowindex);
		}
	}


	@Override
	public void rowRightClick(int rowindex) {
		try {
			Actions action = new Actions(driver);
			action.contextClick(getRowObject(rowindex)).perform();
		} catch (StaleElementReferenceException e) {
			rebuildSelenTable();
			rowRightClick(rowindex);
		}
	}


	@Override
	public void rowHover(int rowindex) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(getRowObject(rowindex)).perform();
		} catch (StaleElementReferenceException e) {
			rebuildSelenTable();
			rowHover(rowindex);
		}
	}

	@Override
	public List<String> getColumnNames() {
		List<WebElement> cols = tableHeader.findElements(By.tagName("th"));
		List<String> colNames = new ArrayList<String>();
		for (WebElement col : cols) {
			colNames.add(col.getAttribute("textContent"));
		}
		return colNames;
	}


	@Override
	public HashMap<String, String> getRow(int rowindex) {
		HashMap<String, String> row = new HashMap<String, String>();
		int cols = getColumnCount();
		List<String> colNames = getColumnNames();

		for (int i = 0; i < cols; i++) {
			String colName = colNames.get(i);
			String value = getCellValue(rowindex, i);
			row.put(colName, value);
		}
		return row;
	}


	@Override
	public boolean isRowExist(HashMap<String, String> row) {
		int rowCount = getRowCount();
		int colcount = row.size();

		for (int i = 0; i < rowCount; i++) {
			HashMap<String, String> tablerow = getRow(i);
			int x = 0;
			for (String key : row.keySet()) {
				x++;
				if (!tablerow.containsKey(key)) {
					return false;
				}
				if (tablerow.get(key).equals(row.get(key))) {
					if (x == colcount) {
						return true;
					}
					continue;
				} else {
					break;
				}
			}
		}
		return false;
	}

}
