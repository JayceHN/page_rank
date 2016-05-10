import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
*  PageRank alogorithm
*
* @author Robin Hormaux & Guillaume Calmant
* @version 0.9
*/
public class main{

  /**
  * main method
  */
  // IMPORT Wrong adj matrix at your own risk !
  //call : java main 'adj_matrix' 'teleport_ratio' 'print (y/n)'
  public static void main(String [] args){
    long startTime = System.currentTimeMillis();
    //ASSERTIONS
    //set the string returned when user use wrongly the program
    String bad_use_msg = "call : java main 'adj_matrix.csv' 'teleport_ratio' 'print (y/n)', please open the readme.";
    //verifying the nubers of args
    if(args.length != 3){
      System.out.println(bad_use_msg);
      return;
    }

    //arg 'print'
    boolean print = false;
    if(args[2].equals("y") || args[2].equals("Y")){
      print = true;
    }

    //arg 'telepor_ratio'
    double telepor_ratio = 0.0;
    try{
      telepor_ratio = Double.parseDouble(args[1]);
    }catch(NumberFormatException e){
      System.out.println("NumberFormatException : impossible to parseDouble. " + e.getMessage() + "\n" + bad_use_msg);
    }
    //More assertions on the ratio...
    if(telepor_ratio > 1.0 || telepor_ratio < 0.0){
      System.out.println("telepor_ratio > 1.0 || telepor_ratio < 0.0 you entered : " + telepor_ratio);
      return;
    }

    //import csv file as an int matrix
	  int [][] page_link = import_csv(args[0],",");

    // determining degree vector of the matrix
    int [] degrees = compute_deg(page_link,print);

    // determining the transition matrix
    double [][] trans = transition(page_link, degrees, 0.1, print);


    long stopTime = System.currentTimeMillis();
    System.out.println("\n Done in : " + (stopTime-startTime) + " !");
  }
  /**
  * An example of a method - replace this comment with your own
  *
  * @param  0.0 < telepor_ratio < 1.0
  * @return     the sum of x and y
  */
  public static double[][] transition(int[][] links, int[] deg, double telepor_ratio, boolean print){
    System.out.print("\n Computing : transition matrix... \n");
    double[][] prob_matrix = new double [links.length][links[0].length];

    //probabilty of jump : alpha/N
    double prob = telepor_ratio/links.length;

    for(int i = 0; i<links.length; i++){       // N
                                               // *
      for(int j = 0; j<links[0].length; j++){  // N

        //evaluating the transition probability:
        prob_matrix[i][j] =  prob + (1.0-telepor_ratio)*links[i][j]/deg[i] ;
      }
    }

    //******************* Code to print in a file *****************
    if(print){
      try{
        FileWriter fw = new FileWriter("transition.csv");
      PrintWriter writer = new PrintWriter(fw);
      double sum = 0;
      System.out.print("\n Printing : transition matrix... \n");
      for (int i = 0; i < prob_matrix.length; i++) {
          for (int j = 0; j < prob_matrix[0].length; j++) {
              writer.print(prob_matrix[i][j]);
              if(j!=prob_matrix[0].length-1){writer.print(",");}
          }
          writer.print("\n");
      }
      writer.close();
    }catch(IOException e){
      e.printStackTrace();
    }
  }
    //******************* $$$$$$$$$$$$$$$$$$$$$$ *****************

    return prob_matrix;
  }

  /**
  * An example of a method - replace this comment with your own
  *
  * @param  y   a sample parameter for a method
  * @return     the sum of x and y
  */
  public static int[] compute_deg(int[][] links, boolean print){
    System.out.print("\n Computing : degrees vector... \n");
    int [] deg = new int [links.length];
    int sum = 0;
    for (int i = 0; i<links.length ;i++ ) {
      for (int j = 0;j<links[0].length ;j++ ) {
        sum = sum + links[i][j];
      }
      deg[i] = sum;
      sum = 0;
    }

    //******************* Code to print in a file *****************
    if(print){
      try{
        FileWriter fw = new FileWriter("degrees.csv");
      PrintWriter writer = new PrintWriter(fw);
      System.out.print("\n Printing : degrees vector... \n");
      for(int i=0; i<deg.length; i++){
        writer.print(deg[i]);
        if(i!=deg.length-1){writer.print(",");}
      }
      writer.close();
    }catch(IOException e){
      e.printStackTrace();
    }
  }
    //******************* $$$$$$$$$$$$$$$$$$$$$$$ *****************

    return deg;
  }

  /**
  * An example of a method - replace this comment with your own
  *
  * @param  y   a sample parameter for a method
  * @return     the sum of x and y
  */
  public static int[][] import_csv(String csv_file, String separator){
    System.out.print("\n Computing : transition matrix... \n");
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
