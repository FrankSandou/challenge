package Challenge.test;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class AppTest
{
   @SuppressWarnings("resource")
   public static void main(String[] args) throws Exception
   {
	   String inputFileName = System.getProperty("user.dir")+"\\data.csv";
	   String outputFileName = System.getProperty("user.dir")+"\\results.csv";
	   List<Line> strList=new ArrayList();

	            Reader reader = Files.newBufferedReader(Paths.get(inputFileName));
	           CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
			   FileWriter writer = new FileWriter(outputFileName,true);

	            for (CSVRecord csvRecord : csvParser) {
	            	if(!csvRecord.get(2).equals("Email"))
	                // Accessing Values by Column Index
	            	{ String name = csvRecord.get(0);
	                String surname = csvRecord.get(1);
	                String email = csvRecord.get(2);
	              strList.add(new Line(name,surname,email)) ;}
	        }
	            Stream<Line> resultStream = strList.stream();
	           Stream<Line> resultStream2 = strList.stream();      
	        Map<String, List<Line>> emails = resultStream.collect(
	                Collectors.groupingBy(Line::getEmail));
	        
	       Map<String, Long> emails2 = resultStream2.collect(
	            Collectors.groupingBy(Line::getEmail, Collectors.counting()));
	       Map<String, Long> result =    emails2.entrySet().stream()
	        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
	        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
	        		(oldValue, newValue)->oldValue,LinkedHashMap::new));	        
	         for(Map.Entry<String, Long> item : result.entrySet()){        
	        	 List<Line> l =  emails.get(item.getKey());
	        	 writer.append(item.getValue().toString());
	        	 writer.append(',');
	        	writer.append(item.getKey());
		        writer.append(',');
	        	String out= ""; 
	            for(Line line : l){
	            out=out.concat(line.getSurname()+" ");     	     
	            }
	            writer.append(out);	           
	            writer.append("\n");
	   
	        }   
writer.flush();
writer.close();
       }
   }
