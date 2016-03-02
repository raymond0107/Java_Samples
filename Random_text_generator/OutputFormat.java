/**
 * OutputFormat class
 *  
 * This class will have two mode to output the answer to the files
 * One is debug mode. 
 * Another is normal mode.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class OutputFormat {
	public static final int MAX_LINE_LENGTH = 80;

	private int prefixLength; 
	private int numWords;
	private String sourceFile;
	private String outFile;
	private PrintWriter out;
	private ArrayList<String> sourceData;

	public OutputFormat() {

	}
	
	/**
  		Initialize the OutputFormat.
  		Given prefixLength, numWords, sourceFile, outFile, sourceData.
	*/
	
	public OutputFormat(int prefixLength, int numWords, String sourceFile, String outFile, ArrayList<String> sourceData) {
		this.prefixLength = prefixLength;
		this.numWords = numWords;
		this.sourceFile = sourceFile;
		this.outFile = outFile;
		this.sourceData = sourceData;
	}

	/**
  		Implements debug mode. 
  		Check the code.
	*/

	public void debug_mode() {
		int row_length = 0;
        int num_of_successor = 0;
        boolean need_Initialize = true;
        String successor = "";
        ArrayList<String> list_Prefix = new ArrayList<String>();
        ArrayList<String> list_successor = new ArrayList<String>();
        RandomTextGenerator generator = new RandomTextGenerator(sourceData, prefixLength);
        generator.creatSuccessor(prefixLength);   
        Prefix prefix = new Prefix();
         
         //get initial prefix and get the first successor.
        while (num_of_successor < numWords) {
            if (need_Initialize) {
               need_Initialize = false;
               list_Prefix = generator.generateInitialPrefix();
               prefix = new Prefix(list_Prefix, prefixLength);     
               successor = generator.chooseSuccessor(prefix);
               System.out.println("DEBUG: chose a new initial prefix:" + prefix.listPrefix());
               System.out.println("DEBUG: prefix:" + prefix.listPrefix());
               System.out.println("DEBUG: successors:" + generator.listSuccessor(prefix));
               System.out.println("DEBUG: word generated:" + successor);
               row_length += successor.length() + 1;
               
               num_of_successor++;
            }
            else {
               list_Prefix.remove(0);
               list_Prefix.add(successor);
               prefix = new Prefix(list_Prefix, prefixLength);
               successor = generator.chooseSuccessor(prefix);
               if (!successor.equals("")) {
                  System.out.println("DEBUG: prefix:" + prefix.listPrefix());
                  System.out.println("DEBUG: successors:" + generator.listSuccessor(prefix));
                  System.out.println("DEBUG: word generated:" + successor);
                  row_length += successor.length() + 1;
                  num_of_successor++;
               }
               else {
                  System.out.println("DEBUG: successors: <END OF FILE>");
                  need_Initialize = true;
               }
            }
        }
	}

	/**
  		Implements normal mode. 
  		implements the text output.
	*/

	public void normal_mode() throws FileNotFoundException{
		PrintWriter out = new PrintWriter(new File(outFile));
		int row_length = 0;
        int num_of_successor = 0;
        boolean need_Initialize = true;
        String successor = "";
        ArrayList<String> list_Prefix = new ArrayList<String>();
        ArrayList<String> list_successor = new ArrayList<String>();
        RandomTextGenerator generator = new RandomTextGenerator(sourceData, prefixLength);
        generator.creatSuccessor(prefixLength);   
        Prefix prefix = new Prefix();
         
         //get initial prefix and get the first successor.
        while (num_of_successor < numWords) {
            if (need_Initialize) {
               need_Initialize = false;
               list_Prefix = generator.generateInitialPrefix();
               prefix = new Prefix(list_Prefix, prefixLength);     
               successor = generator.chooseSuccessor(prefix);
               row_length += successor.length() + 1;
               if (row_length > MAX_LINE_LENGTH) {
                  row_length = successor.length() + 1;
                  out.write("\n");
               }
               out.write(successor + " ");
               num_of_successor++;
            }
            else {
               list_Prefix.remove(0);
               list_Prefix.add(successor);
               prefix = new Prefix(list_Prefix, prefixLength);
               successor = generator.chooseSuccessor(prefix);
               if (!successor.equals("")) {
                  row_length += successor.length() + 1;
                  if (row_length > MAX_LINE_LENGTH) {
                     row_length = successor.length() + 1;
                     out.write("\n");
                  }
                  out.write(successor + " ");
                  num_of_successor++;
               }
               else {
                  need_Initialize = true;
               }
            }
        }
        out.close();
	}
}