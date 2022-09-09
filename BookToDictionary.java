import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class BookToDictionary {

    private static final int LOWER_A = (int) 'a';
    private static final int LOWER_Z = (int) 'z';


    /**
     * Produces a Scanner connected to a text file accessible via the web.
     *
     * The method expects a link to a text file. When accessing material from
     * Project Gutenberg it is import to use the plain text version of a book.
     *
     * DO NOT MODIFY THIS METHOD.
     *
     * @param link String with URL to text file.
     * @return a Scanner for the file or null if connection cannot be made.
     */

    //{method browseTextFile} to return scanner to use in dictionary later in program
    public final static Scanner browseTextFile(final String link) {
        // Declare the return variable
        Scanner fileOnline;
        // Use try/catch to prevent the program from crashing.
        try {
            // Create a URL object from the link provided
            URL url = new URL(link);
            // Turn the URL into a Scanner
            fileOnline = new Scanner(url.openStream());
        } catch (IOException e) {
            // If something goes wrong, prepare to return null Scanner.
            fileOnline = null;
        }
        return fileOnline;
    }

    // {method checkDuplicate} to check for duplicate words
    public static Boolean checkDuplicate(final String s, String[] a) {
        // Declare the return variable
        Boolean duplicate = false;
        // for loop to iterate through array
        for (int i = 0; i < a.length; i++) {
            // if the string at current index is equal to the word being checked
            if (a[i] == s) {
                // set duplicate to true
                duplicate = true;
                // exits the for loop
                i = a.length + 1;
            }
        }
        return duplicate;
    }

    public static String sanitize(final String s) {
        // Declare the return variable
        String result = "";
        // Declare new String, make contents {string s} to lowercase, put in {string sLC}
        String sLC = s.toLowerCase();
        // for loop to scan each character sequentially
        for (int i = 0; i < sLC.length(); i++) {
            // new int asciiCode, set equal to index of sLC (cast as int to return nearest whole number)
            int asciiCode = (int) sLC.charAt(i);
            // for charAt(i), if letter add to {string result}
            if (asciiCode >= LOWER_A && asciiCode <= LOWER_Z)
                // add chars in sequential order in which you received if they are an alphabetical letter
                result = result + sLC.charAt(i);
        }
        return result;
    }

    // method addWord to add a word to the dictionary
    public static void addWord(final String s, String[] array) {
        // take contents from {String cleaned} and copy to {String addToDict}
        String addToDict = s;
        // for loop to check for next available spot in array
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = addToDict;
                // using this instead of a break statement
                i = array.length +1;
            }
        }
    }

    // method makeArray to count how many possible words could be in the file to create the array of the size we need
    public static String[] makeArray(final String s) {
        // declare counter variable
        int counter = 0;
        // put {string s} into {string book}
        String book = s;
        // declare scanner to go through each word
        Scanner scanFile = browseTextFile(book);
        // while the scanner still has words...
        while (scanFile.hasNext()) {
            // increment counter
            counter++;
        }
        // declare array with size specified by counter
        String[] array = new String[counter];
        // return the array
        return array;
    }

    // method printDict to print out the words in the array
    public static void printDict(String[] a) {
        // iterate through the array sequentially, printing each one on a new line
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + "\n");
        }
    }

    // method scanFile to use scanner from {method browseTextFile} to substring words and send to {method sanitize}
    public static void scanFile(final String s) {
        // get text contents from {String book} in {method main} to use in {method scanFile}
        String book = s;
        // call {method makeArray} to create array of correct size
        String[] array = makeArray(s);
        // Declare the return variable
        String result = "";
        // Declare new scanner with the scanner Prof. wrote
        Scanner scanFile = browseTextFile(book);
        // while the webpage has words...
        while(scanFile.hasNext()) {
            // pull one word and put into {String str}
            String needsCleaning = scanFile.next();
            // call {method sanitize} to make LC and remove non letters (returns String into {String cleaned})
            String cleaned = sanitize(needsCleaning);
            // call {method checkDuplicate} and get boolean return
            Boolean duplicate = checkDuplicate(cleaned, array);
            // if duplicate returns false, {method addWord}
            if (!duplicate) {
                addWord(cleaned, array);
            }
            // else, return to beginning of while loop and scan next word, repeat process
        }
        printDict(array);
    }

    /** Use main() to call other methods; don't put all your code in main. */
    public static void main(String[] args) {
        // Put the URL of the book in here
        String book = "https://www.gutenberg.org/files/98/98-0.txt";
        scanFile(book);
    }  // method main

}  // class BookToDictionary
