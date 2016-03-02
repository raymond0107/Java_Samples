/**
 * GenText class
 * 
 * This contains the main method. 
 * This class will have a main that's responsible for processing 
 * the command-line arguments, opening and closing the files, 
 * and handling any errors related to the above tasks. 
 * All the other functionality will be delegated to other object(s) 
 * created in main and their methods.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
public class GenText {
   
   public static void main(String[] args) {
      final int NUM_PARAMETER = 4;
      int numOfParameter = 0;
      int prefixLength = 0;
      int numWords = 0;
      String sourceFile = "";
      String outFile = "";
      boolean debug_mode = false;
      ArrayList<String> sourceData = new ArrayList<String>();
      OutputFormat output = new OutputFormat();
      try 
      {
         for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("-d")) {
               debug_mode = true;
            }
            else {
               numOfParameter++;
               if (numOfParameter == 1) {
                  prefixLength = Integer.parseInt(arg);
                  if (prefixLength < 1) {
                     System.out.println("prefixLength should be postive");
                     usage(); 
                     return;
                  }
               }
               else if (numOfParameter == 2) { 
                  numWords = Integer.parseInt(arg);
                  if (numWords < 0) {
                     System.out.println("numWords should be zero or postive");
                     usage(); 
                     return;
                  }
               }
               else if (numOfParameter == 3) { 
                  sourceFile = arg; 
               }
               else if (numOfParameter == 4) { 
                  outFile = arg; 
               }
            }
         }
         if (numOfParameter != NUM_PARAMETER) {
            System.out.println("missing command-line arguments");
            usage(); 
            return; 
         }
         
         //get parameter.       
         sourceData = readFile(sourceFile);
         if (prefixLength >= sourceData.size()) {
            System.out.println("prefixLength should be less than number of words in sourceFile");
            return;
         }
         output = new OutputFormat(prefixLength, numWords, sourceFile, outFile, sourceData);
         if (debug_mode) {
            output.debug_mode();
         }
         else {
            output.normal_mode();
         }
      }
      catch (FileNotFoundException exception)
      {  
         System.out.println("File not found: " + sourceFile);
      }
      catch (IOException exception)
      {
         exception.printStackTrace();
      }
      catch (NumberFormatException exception) {
         System.out.println("prefixLength or numWords arguments are not integers.");
         usage(); 
      } 
      
   }

   public static ArrayList<String> readFile(String filename) throws IOException{
      Scanner in = new Scanner(new File(filename));
      ArrayList<String> sourceData = new ArrayList<String>();
      while (in.hasNext()) {
         sourceData.add(in.next());
      }
      in.close();
      return sourceData;
   }

   public static void usage() {
      System.out.println("Usage: java GenText [-d] prefixLength numWords sourceFile outFile");
   }
}