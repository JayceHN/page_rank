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
  //call : java main csvfile
  public static void main(String [] args){
    //import csv file as an int matrix
	  int [][] page_link = import_csv(args[0],",",false);

    // determining degree vector of the matrix
    int [] degrees = compute_deg(page_link,false);

    // determining the transition matrix
    double [][] trans = transition(page_link, degrees, 0.1, false);



  }
  /**
  * An example of a method - replace this comment with your own
  *
  * @param  0.0 < telepor_ratio < 1.0
  * @return     the sum of x and y
  */
  public static double[][] transition(int[][] links, int[] deg, double telepor_ratio, boolean print){
    double[][] prob_matrix = new double [links.length][links[0].length];
    double prob = telepor_ratio/links.length;
    for(int i = 0; i<links.length; i++){
      for(int j = 0; j<links[0].length; j++){
        prob_matrix[i][j] = (1.0-telepor_ratio)* links[i][j]/deg[i] + prob;
      }
    }

    if(print){
      double sum = 0;
      System.out.print("\n%%%%%%%%%% PRINT==TRUE : TRANSITION %%%%%%%%%%\n");
      for (int i = 0; i < prob_matrix.length; i++) {
          for (int j = 0; j < prob_matrix[0].length; j++) {
              System.out.print(prob_matrix[i][j] + " ");
              sum += prob_matrix[i][j];
          }
          System.out.print("\n");
      }
    }
    return prob_matrix;
  }

  /**
  * An example of a method - replace this comment with your own
  *
  * @param  y   a sample parameter for a method
  * @return     the sum of x and y
  */
  public static int[] compute_deg(int[][] links, boolean print){
    int [] deg = new int [links.length];
    int sum = 0;
    for (int i = 0; i<links.length ;i++ ) {
      for (int j = 0;j<links[0].length ;j++ ) {
        sum = sum + links[i][j];
      }
      deg[i] = sum;
      sum = 0;
    }

    if(print){
      System.out.print("\n%%%%%%%%%% PRINT==TRUE : COMPUTE_DEG %%%%%%%%%%\n");
      for(int x: deg){
        System.out.print(x+" ");
      }
      System.out.print("\n");
    }
    return deg;
  }

  /**
  * An example of a method - replace this comment with your own
  *
  * @param  y   a sample parameter for a method
  * @return     the sum of x and y
  */
  public static int[][] import_csv(String csv_file, String separator,boolean print){
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

  if(print){
    System.out.print("\n%%%%%%%%%% PRINT==TRUE : IMPORT_CSV %%%%%%%%%%\n");
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[0].length; j++) {
            System.out.print(matrix[i][j] + " ");
        }
        System.out.print("\n");
    }
  }
    return matrix;
  }
}
