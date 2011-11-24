/**
 *  @author Xiao G. Wu
 *  CS111A - Assignment 12
 *  @version 1.0 11/18/2011
 *  NOTE: This code does not account for // comments after a ;
 *        I also decided to not count the ;'s in for loops
 */

import java.io.*;

public class CodeParser {
    public static void main (String[] args) {
        // Check to make sure the user enters exactly one argument for sourceFile.
        // If not provide usage.
        if (args.length < 1 || args.length > 1) {
            System.out.println("Please specify one argument");
            System.out.println("Usage: CodeParser sourceFile");
            System.exit(1);
        }
        else {
            try {
                // EC test cases
                // Here is a comment with ;
                // Here is a comment with {
                // Here is a comment with }
                /* Multi-line comment start
                }
                */
                /* Here are some comments with ; and { } */
                String currentLine;
                int statements = 0;
                int nestedBlocks = 0;
                // Deepest Nested code block
                int maxNest = 0; 
                // Flag to ignore content in between multi-line comments
                boolean inBetweenComments = false; 
                BufferedReader br = new BufferedReader(new FileReader(args[0]));
                while ((currentLine = br.readLine()) != null) {
                    // System.out.println(currentLine);
                    // Trim whitespace from the front and back of line
                    currentLine = currentLine.trim(); 
                    // Check for multi-line comment start
                    if (currentLine.startsWith("/*"))
                       inBetweenComments = true; 
                    if (!inBetweenComments && !currentLine.startsWith("//")) {     
                        if (currentLine.matches(".*;$")) {
                            statements++;
                            // System.out.println("s:" + statements);
                        }
                        if (currentLine.matches(".*\\{$")) {
                            nestedBlocks++;
                        }
                        // Determine the deepest nested block
                        maxNest = Math.max(maxNest, nestedBlocks);
                        if (currentLine.matches(".*\\}$")) {
                            nestedBlocks--;
                        }
                    }
                    // Check for multi-line comment end
                    if (currentLine.endsWith("*/"))
                        inBetweenComments = false;
                }
                System.out.println(args[0] + " has " + statements + " statements and its deepest nested block is " + maxNest + " levels deep.");
                // Close file
                br.close();
            } catch (IOException e) {
                System.out.println("Error: " + e);
                System.exit(1);
            }
        }
    }
}
