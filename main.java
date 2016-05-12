import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;

/**
*  PageRank alogorithm
* Inspired from [Pearson] - Algorithms, 4th ed. - [Sedgewick, Wayne]
* @author Robin Hormaux & Guillaume Calmant
* @version 0.9
*/
public class main{

  /**
  * main method
  */
  // IMPORT Wrong format adj matrix at your own risk !
  //call : java main 'adj_matrix' 'teleport_ratio' 'print (y/n)'
  public static void main(String [] args){
    long startTime = System.currentTimeMillis();
    //ASSERTIONS
    //set the string returned when user use wrongly the program
    String bad_use_msg = "call : java main 'adj_matrix.csv' 'teleport_ratio' 'print (y/n)' 'perso.csv', please open the readme.";
    //verifying the nubers of args
    if(args.length != 4){
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
      return;
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
    double [][] trans = new double[page_link.length][page_link[0].length];
    double[] perso = new double[degrees.length];
    if(args[3].equals("null")){
      trans = transition(page_link, degrees, telepor_ratio, perso, print);
    }else{
      perso = import_perso(args[3],",");
      trans = transition(page_link, degrees, telepor_ratio, perso, print);
    }
    power(trans);

    long stopTime = System.currentTimeMillis();
    System.out.println("\n Done in : " + (stopTime-startTime) + "ms !");
  }

  /**
  * An example of a method - replace this comment with your own
  *
  * @param  0.0 < telepor_ratio < 1.0
  * @return     the sum of x and y
  */
  public static double[][] transition(int[][] links, int[] deg, double telepor_ratio, double[] perso, boolean print){
    // google matrix
    System.out.print("\n Computing : transition matrix... \n");
    double[][] prob_matrix = new double [links.length][links[0].length];
    int perso_flag = 0;
    for(double x: perso){
      if(x!=0){
        perso_flag = 1;
        break;
      }
    }
    // probabilty of jump : alpha/N
    double prob = telepor_ratio/links.length;
    if(perso_flag==0){
      for(int i = 0; i<links.length; i++){       // N
        for(int j = 0; j<links[0].length; j++){  // N
          //avoid sink nodes
          if(deg[i]==0){
            for(int k=0; k<links.length;k++){
              links[i][k] = 1/links.length;
            }
            deg[i]=1;
          }
            //evaluating the transition probability (no more sparse):
            prob_matrix[i][j] =  prob + (1.0-telepor_ratio)*links[i][j]/deg[i] ;

        }
      }
    }else {
      for(int i = 0; i<links.length; i++){       // N
        for(int j = 0; j<links[0].length; j++){  // N
          //avoid sink nodes
          //avoid sink nodes
          if(deg[i]==0){
            for(int k=0; k<links.length;k++){
              links[i][k] = 1/links.length;
            }
            deg[i]=1;
          }
            //evaluating the transition probability (no more sparse):
            prob_matrix[i][j] =  prob + (1.0-telepor_ratio)*links[i][j]/deg[i]*perso[i] ;
          }
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
  public static void power(double [][] trans){
    System.out.print("\n Computing : surfer simulation ... \n");
    int length = trans.length;


    // Use the power method to compute page ranks.
    double[] rank = new double[length];
    // first move
    rank[0] = 1.0;

    int iter ;
    for (iter = 0 ; true ; iter++) {
      double[] tmp = new double[length];
      for (int i = 0; i < length; i++) {
        for (int j = 0; j < length; j++)
        //multiply the the matrix by the vector and record the result in tmp
        tmp[i] += trans[j][i]*rank[j];
      }
      // if converge or iter is too large break the loop
      if (converge(rank,tmp) || iter == Integer.MAX_VALUE-1){
        break;
      }
      rank = tmp;
    }

    rank = normalize(rank);
    System.out.println("\n Score after " + iter + " iterations : \n");
    // print page ranks
    double sum = 0;
    for (int i = 0; i < trans.length; i++) {
      System.out.println(" " + i + " " + rank[i]);
      sum += rank[i];
    }
    System.out.println("\n sum = " + sum);
  }


  /**
  * Calcule si c'est convergent
  */
  public static boolean converge(double [] rank, double [] newrank){
    int n = rank.length;
    for (int i = 0; i < n; i++){
      if(rank[i] <= newrank[i]-0.00000000000000005)
      return false;
    }
    return true;
  }

  /**
  * Normalize
  */
  public static double[] normalize(double [] rank){
    double norm = 0 ;
    for(double x: rank){
      norm += x*x;
    }
    norm = Math.sqrt(norm);
    double [] normalized = new double[rank.length];
    for (int i =0; i<rank.length ;i++ ) {
      normalized[i] = rank[i]/norm;
    }
    return normalized;
  }

  /**
  * An example of a method - replace this comment with your own
  *
  * @param  y   a sample parameter for a method
  * @return     the sum of x and y
  */
  public static int[] compute_deg(int[][] links, boolean print){
    System.out.print("\n Computing : degrees vector... \n");
    // degrees are just the sum of the each lines
    int [] deg = new int [links.length];
    int sum = 0;
    for (int i = 0; i<links.length ;i++ ) {
      //running trough the line
      for (int j = 0;j<links[0].length ;j++ ) {
        sum += links[i][j];
      }
      //assign the column i, the sum of the line i
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
  public static double[] import_perso(String csv_file, String separator){
    System.out.print("\n Importing : Perso vector ... \n");
    //We use ArrayList because the size is unknown
    ArrayList<String[]> list= new ArrayList<String[]>();

    try{
      // reading line by line the csv file (comma seperated variables)
      BufferedReader CSVFile = new BufferedReader(new FileReader(csv_file));
      String line = CSVFile.readLine();
      while(line!=null){
        list.add(line.split(separator));
        line = CSVFile.readLine();
      }
    }catch(IOException e){
      e.printStackTrace();
    }
    // casting to string matrix
    String [][] string_matrix = list.toArray(new String[list.size()][]);
    // casting to integer matrix
    double [] vector = new double [string_matrix[0].length];
    for (int j = 0; j < string_matrix[0].length; j++) {
      vector[j] = Double.parseDouble(string_matrix[0][j]);
    }
    return vector;
  }


  /**
  * An example of a method - replace this comment with your own
  *
  * @param  y   a sample parameter for a method
  * @return     the sum of x and y
  */
  public static int[][] import_csv(String csv_file, String separator){
    System.out.print("\n Importing : CSV file ... \n");
    //We use ArrayList because the size is unknown
    ArrayList<String[]> list= new ArrayList<String[]>();
    try{
      // reading line by line the csv file (comma seperated variables)
      BufferedReader CSVFile = new BufferedReader(new FileReader(csv_file));
      String line = CSVFile.readLine();
      while(line!=null){
        list.add(line.split(separator));
        line = CSVFile.readLine();
      }
    }catch(IOException e){
      e.printStackTrace();
    }
    // casting to string matrix
    String [][] string_matrix = list.toArray(new String[list.size()][]);
    // casting to integer matrix
    int [][] matrix = new int[string_matrix.length][string_matrix[0].length];
    for (int i = 0; i < string_matrix.length; i++) {
      for (int j = 0; j < string_matrix[0].length; j++) {
        matrix[i][j] = Integer.parseInt(string_matrix[i][j]);
      }
    }
    return matrix;
  }
}
