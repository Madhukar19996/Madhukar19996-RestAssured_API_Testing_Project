package day10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class DataProviders {
	
	@DataProvider
	public Object[][] excelDataProvider() throws IOException {
	    String path = ".\\testdata\\BookingData.xlsx";
	    ExcelUtils xl = new ExcelUtils(path, "Sheet1");

	    int rownum = xl.getRowCount();
	    int colcount = xl.getCellCount(1);

	    Object dataArray[][] = new Object[rownum][9]; // 9 parameters expected

	    for (int i = 1; i <= rownum; i++) {
	        dataArray[i - 1][0] = xl.getCellData(i, 0); // username
	        dataArray[i - 1][1] = xl.getCellData(i, 1); // password
	        dataArray[i - 1][2] = xl.getCellData(i, 2); // firstname
	        dataArray[i - 1][3] = xl.getCellData(i, 3); // lastname

	        // Parse totalprice to int
	        dataArray[i - 1][4] = Integer.parseInt(xl.getCellData(i, 4));

	        // Parse depositpaid to boolean (case-insensitive match)
	        dataArray[i - 1][5] = Boolean.parseBoolean(xl.getCellData(i, 5).toLowerCase());

	        dataArray[i - 1][6] = xl.getCellData(i, 6); // checkin
	        dataArray[i - 1][7] = xl.getCellData(i, 7); // checkout
	        dataArray[i - 1][8] = xl.getCellData(i, 8); // additionalneeds
	    }

	    return dataArray;
	}

	
	@DataProvider
	public Object[][] jsonDataProvider() throws IOException {
		// Path to your JSON file
		String filePath = ".\\testData\\bookingData.json";

		// Read JSON file and map it to a List of Maps
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, String>> dataList = objectMapper.readValue(new File(filePath),
				new TypeReference<List<Map<String, String>>>() {
		});

		// Convert List<Map<String, String>> to Object[][]
		Object[][] dataArray = new Object[dataList.size()][];
		for (int i = 0; i < dataList.size(); i++) {
			dataArray[i] = new Object[] { dataList.get(i) };
		}

		return dataArray;
	}


	@DataProvider
	public Object[][] csvDataProvider() throws IOException {
	    String filePath = ".\\testdata\\BookingData.csv";
	    List<Object[]> dataList = new ArrayList<>();

	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        br.readLine(); // skip header
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(",");

	            // Clean and convert data types
	            String username = parts[0].trim();
	            String password = parts[1].trim();
	            String firstname = parts[2].trim();
	            String lastname = parts[3].trim();
	            int totalprice = Integer.parseInt(parts[4].trim());
	            boolean depositpaid = Boolean.parseBoolean(parts[5].trim());
	            String checkin = parts[6].trim();
	            String checkout = parts[7].trim();
	            String additionalneeds = parts[8].trim();

	            // Add all values as expected by the test method
	            dataList.add(new Object[]{
	                username, password, firstname, lastname,
	                totalprice, depositpaid, checkin, checkout, additionalneeds
	            });
	        }
	    }

	    // Convert to Object[][]
	    Object[][] dataArray = new Object[dataList.size()][];
	    for (int i = 0; i < dataList.size(); i++) {
	        dataArray[i] = dataList.get(i);
	    }

	    return dataArray;
	}







}
