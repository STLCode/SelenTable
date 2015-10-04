# SelenTable

Web Tables are simple but powerful data presentation view,  it is one of the most important DOM WebElement required by most of the web applications, usually leveraged for displaying reports and analytical data, but at times it also used for website layout designing, but it is highly discouraged nowadays due to its non-responsiveness, DIV’s have replaced tables for responsive websites layout designing, Nevertheless.

Today we are going to see one of the best approaches working with HTML Web tables with Selenium; we are calling it SelenTable (Interface) (a plugin to SelenQuery Framework)

For this post, I would be positing some generic example codes of SelenTable, which could be used without SelenQuery framework.

SelenTable feature:

It’s a StaleElementReferenceException Proof Webtable API which provides a rich sets of API methods to work with WebTable efficiently.

* Provide a set of API for manipulating traversing through Table rows and columns.
* Reading values from cells
* Getting object inside cells and working on them.
* Getting count or rows and columns
* Clicking and double-clicking on rows.
* Method names are self-explanatory.
* Open Source code available under Apache License.

Let’ look at it.


```
int getRowCount(); 
int getColumnCount();

WebElement getRowObject(int rowindex); // it will return the TR WebElement 
WebElement getRowObject(String colHeaderText, String uniqueValue); 

int getRowIndex(String colHeaderText, String uniqueValue); //return zero based row index based on its display value 
int getColIndexfromDisplayHeader(String colHeaderText); //return zero based index

WebElement getCellObject(int rowindex, int colIndex); //return TD WebElement 
WebElement getCellObject(int rowindex, String colHeaderText);
WebElement getControlInsideCell(int rowindex, String colHeaderText, By innerControlSelector); //searches WebElement inside TD

String getCellValue(int rowindex, int colIndex); // returns the text indside a cell
String getCellValue(int rowindex, String colHeaderText);

void rowClick(int rowindex);
void rowDoubleClick(int rowindex);
void rowRightClick(int rowindex);
void rowHover(int rowindex);

List<String> getColumnNames(); //return a list of column Names
HashMap<String, String> getRow(int rowindex); //return HashMap of columName, 
											  //value for the given row index

boolean isRowExist(HashMap<String, String> row); // you can pass the above hashmap with all or 
												 //less columns to check if those are exists

```

#Usage 

```
import com.stl.selenquery.selentable.SelenTable;
import com.stl.selenquery.selentable.SelenWebTable;


public class Test {

	public static void main(String[] args) {
				
		SelenTable selenTable = new SelenWebTable(driver, by);
		int iRowCount = selenTable.getRowCount(); 
		int iColCount = selenTable.getColumnCount();
		for(int i=0;i<=iRowCount;i++){
			for(int j=0;j<=iColCount;i++){
				System.out.println(selenTable.getCellValue(i, j));
			}	
			
		}
		
	}

}
```


Visit blog for more information 
[STL BLOG](https://www.softtechlabs.com)

