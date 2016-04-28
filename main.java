import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
*  PageRank alogorithm
*
* @author Robin Hormaux & Guillaume Calmant
* @version 0.1
*/
public class main{
  // instance variables - replace the example below with your own
  // private int x;

  /**
  * Constructor for objects of class main
  */
  public static void main(String [] args){
	  int [][] matrix = import_csv(args[0],",");
	  for (int i = 0; i < matrix.length; i++) {
		    for (int j = 0; j < matrix[0].length; j++) {
		        System.out.print(matrix[i][j] + " ");
		    }
		    System.out.print("\n");
		}
  }

  /**
  * An example of a method - replace this comment with your own
  *
  * @param  y   a sample parameter for a method
  * @return     the sum of x and y
  */
  public static int[][] import_csv(String csv_file, String separator){
	  ArrayList<String[]> list= new ArrayList<String[]>();
	  try{
	  BufferedReader CSVFile = new BufferedReader(new FileReader(csv_file));
	  String line = CSVFile.readLine();
	  while(line!=null){
		  list.add(line.split(separator));
		  line = CSVFile.readLine();
	  }
	  }catch(IOException e){
		  e.printStackTrace();
	  }
	  String [][] string_matrix = list.toArray(new String[list.size()][]);
	  int [][] matrix = new int[string_matrix.length][string_matrix[0].length];
	  for (int i = 0; i < string_matrix.length; i++) {
		for (int j = 0; j < string_matrix[0].length; j++) {
			matrix[i][j] = Integer.parseInt(string_matrix[i][j]);
		}
	}
    return matrix;
  }
}
