
/**
 *
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Administrator
 *
 */
public class cpp {

  private static final String SUFFIX = "SUFFIX";
  private static final String PREFIX = "PREFIX";
  private static final String RPLSPLCHARS = "RPLSPLCHARS";
  private static final String RPLNUMBERS = "RPLNUMBERS";
  private static final int MAX_PWD_LENGTH = 9;//8;
  private static Set<String> createdFiles = new HashSet<String>();

  /**
   * @param args
   */
  public static void main(String[] args) {
    try {
      System.out.println("Welcome to Common Password Permutations (CPP)");
      System.out.println("-----------------------------------");
      System.out
        //.println("Please enter a word of 5 to 8 letters. The final generate word list will be saved to a .txt file in the directory of this program:");
        .println("Please enter a word of 4 to 9 letters. The final generated word list will be saved to a .txt file in the directory of this program:");

      BufferedReader br = new BufferedReader(new InputStreamReader(
        System.in));
      String inputStr = br.readLine();
      if (inputStr.length() < 4 || inputStr.length() > 9) {
        System.out
          .println("Please enter words of length between 4 and 9 only : " + inputStr + "\n");
      } else {
        System.out
          .print("\nPlease enter an option (1-5): ");
        String option = br.readLine();

        getWordPermutations(inputStr, option);

        System.out.println("*******************************************");
        System.out
          .println("* The file(s) containing the text \n* above is now in the directory that this \n* program was run from.");
        System.out.println("* Thank you drive through :-)");
        System.out.println("*******************************************");
        System.out.println();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void getWordPermutations(String word, String option) {
    /*if (word.length() < 4 || word.length() > 9) {
      System.out
        .println("Please enter words of length between 5 and 8 only : " + word + "\n");
    } else {*/
    String fileName = word + ".txt";
    Set<String> wordList = createWordList(word, option);
    writeListtoFile(wordList, fileName);
    // }
  }

  private static Set<String> readFile(String fileName) {
    File file = new File(fileName);
    Set<String> wordList = new HashSet<String>();
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      String line = null;
      while ((line = br.readLine()) != null) {
        if (!line.isEmpty()) {
          wordList.add(line.trim());
        }
      }
      br.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found : " + fileName);
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("Problem in reading from file : " + fileName);
      e.printStackTrace();
    }
    return wordList;
  }

  private static Set<String> createWordList(String word, String option) {
    Set<String> wordlist = new HashSet<String>();
    executeRules(word, wordlist, option);
    Set<String> finalStrings = new HashSet<String>();
    finalStrings.addAll(wordlist);
    if (option.compareTo("2") >= 0) {
      for (String rec : wordlist) {
        executeRules(rec, finalStrings, option, false);
      }
      for (int i = 0; i < 10; i++) {
        // to get 1000s of permutations remove above line
        wordlist.addAll(finalStrings);
        for (String rec : wordlist) {
          executeRules(rec, finalStrings, option, false);
        }
      }

    }

    // repeat the word so it’s doubled
    finalStrings.add(word + word);

    return finalStrings;
  }

  private static void executeRules(String word, Set<String> wordlist, String option) {
    executeRules(word, wordlist, option, true);
  }

  private static void executeRules(String word, Set<String> wordlist,
    String option,
    boolean append) {
    if (append) {
      // prefix with numbers: 1,12,123
      wordlist.addAll(addNumbers(word, PREFIX));
      // suffix with numbers: 1,12,123
      wordlist.addAll(addNumbers(word, SUFFIX));

      // append spl chars '!', '?', '*', '-', '9' before and after word
      wordlist.addAll(appendSplChars(word, new char[]{'!', '?', '*', '-', '9'}));

      if (option.equals("3")) {
        // append spl chars '.', '£', '$', '%' before and after word
        wordlist.addAll(appendSplChars(word, new char[]{'.', '£', '$', '%'}));
      } else if (option.equals("4")) {
        // prefix with numbers: 1940 to 2019
        wordlist.addAll(addNumbers2(word, PREFIX));
        // suffix with numbers: 1940 to 2019
        wordlist.addAll(addNumbers2(word, SUFFIX));

        // prefix with numbers: 00 to 99
        wordlist.addAll(addNumbers3(word, PREFIX));
        // suffix with numbers: 00 to 99
        wordlist.addAll(addNumbers3(word, SUFFIX));
      } else if (option.equals("5")) {
        // append spl chars '.', '£', '$', '%' before and after word
        wordlist.addAll(appendSplChars(word, new char[]{'.', '£', '$', '%'}));

        // prefix with numbers: 1940 to 2019
        wordlist.addAll(addNumbers2(word, PREFIX));
        // suffix with numbers: 1940 to 2019
        wordlist.addAll(addNumbers2(word, SUFFIX));

        // prefix with numbers: 00 to 99
        wordlist.addAll(addNumbers3(word, PREFIX));
        // suffix with numbers: 00 to 99
        wordlist.addAll(addNumbers3(word, SUFFIX));
      }
    }

    // replace 'h', 'i' with '#', '!' resp.
    wordlist.addAll(replaceScharsNos(word, RPLSPLCHARS));
    // replace 'i', 'o', 'e', 'a' with '1', '0','3' resp.
    wordlist.addAll(replaceScharsNos(word, RPLNUMBERS));
    // convert first, last, alternative letters to uppercase
    wordlist.addAll(makeUpperCase(word));
  }

  private static Set<String> appendSplChars(String word, char[] splChars) {
    Set<String> wordlist = new HashSet<String>();
    for (int i = 0; i < splChars.length; i++) {
      char splChar = splChars[i];
      validateNAdd(wordlist, splChar + word);
      validateNAdd(wordlist, word + splChar);
      //validateNAdd(wordlist, splChar + word + splChar);
    }
    return wordlist;
  }

  private static Set<String> addNumbers(String word, String option) {
    Set<String> wordlist = new HashSet<String>();
    int gap = MAX_PWD_LENGTH - word.length();
    for (int i = 1; i <= gap; i++) {
      String apdWord = "";

      for (int j = 1; j <= i; j++) {
        apdWord += j;
      }

      if (PREFIX.equals(option)) {
        validateNAdd(wordlist, apdWord + word);
      } else {
        validateNAdd(wordlist, word + apdWord);
      }
    }
    return wordlist;
  }

  private static Set<String> addNumbers2(String word, String option) {
    Set<String> wordlist = new HashSet<String>();
    for (int j = 1940; j <= 2019; j++) {
      String apdWord = String.valueOf(j);

      if (PREFIX.equals(option)) {
        validateNAdd(wordlist, apdWord + word);
      } else {
        validateNAdd(wordlist, word + apdWord);
      }
    }
    return wordlist;
  }

  private static Set<String> addNumbers3(String word, String option) {
    Set<String> wordlist = new HashSet<String>();

    for (int j = 0; j <= 99; j++) {
      String apdWord = String.format("%02d", j);

      if (PREFIX.equals(option)) {
        validateNAdd(wordlist, apdWord + word);
      } else {
        validateNAdd(wordlist, word + apdWord);
      }
    }
    return wordlist;
  }

  private static void validateNAdd(Set<String> wordLst, String word) {
    //if (word.length() <= MAX_PWD_LENGTH) {
    wordLst.add(word);
    //}
  }

  private static Set<String> makeUpperCase(String word) {
    Set<String> toUpperCase = new HashSet<String>();
    validateNAdd(toUpperCase, word.toUpperCase());
    int length = word.length();
    validateNAdd(toUpperCase,
      word.substring(0, 1).toUpperCase() + word.substring(1, length));
    validateNAdd(toUpperCase, word.substring(0, length - 1)
      + word.substring(length - 1, length).toUpperCase());

    boolean odd = true;
    for (int j = 0; j < 2; j++) {

      char[] chars = alternateUC(word, length, odd);
      odd = false;
      validateNAdd(toUpperCase, new String(chars));
    }

    return toUpperCase;
  }

  private static char[] alternateUC(String word, int length, boolean odd) {
    char[] chars = word.toCharArray();
    for (int i = 0; i < length; i++) {
      if (odd && i % 2 == 0) {
        chars[i] = Character.toUpperCase(chars[i]);
      }
      if (!odd && i % 2 != 0) {
        chars[i] = Character.toUpperCase(chars[i]);
      }
    }
    return chars;
  }

  private static Set<String> replaceScharsNos(String word, String option) {
    Set<String> toUpperCase = new HashSet<String>();

    for (int k = 0; k < word.length(); k++) {

      String subStr = word.substring(k);

      for (int i = 0; i <= subStr.length(); i++) {
        char[] chars = subStr.toCharArray();
        for (int j = 0; j < i; j++) {
          if (RPLSPLCHARS.equals(option)) {
            replaceComb1(chars, j);
          } else if (RPLNUMBERS.equals(option)) {
            replaceComb2(chars, j);
          }

        }
        String finalWord = new String(chars);
        if (k > 0) {
          finalWord = word.substring(0, k) + finalWord;
        }
        if (!finalWord.equals(word)) {
          validateNAdd(toUpperCase, finalWord);
        }
      }
    }
    return toUpperCase;
  }

  private static void replaceComb1(char[] chars, int j) {
    if (chars[j] == 'i') {
      chars[j] = '!';
    } else if (chars[j] == 'h') {
      chars[j] = '#';
    } else if (chars[j] == 'o') {
      chars[j] = '0';
    } else if (chars[j] == 'e') {
      chars[j] = '3';
    } else if (chars[j] == 'a') {
      chars[j] = '@';
    }
  }

  private static void replaceComb2(char[] chars, int j) {
    if (chars[j] == 'h') {
      chars[j] = '#';
    } else if (chars[j] == 'i') {
      chars[j] = '1';
    } else if (chars[j] == 'o') {
      chars[j] = '0';
    } else if (chars[j] == 'e') {
      chars[j] = '3';
    } else if (chars[j] == 'a') {
      chars[j] = '@';
    }
  }

  private static void writeListtoFile(Set<String> wordList, String fileName) {
    for (String cFilename : createdFiles) {
      if (fileName.equalsIgnoreCase(cFilename)) {
        fileName = fileName.replace(".txt", "") + "_1" + ".txt";
      }
    }
    File file = new File(fileName);
    PrintWriter pw = null;
    List<String> list = new ArrayList<String>(wordList);
    Collections.sort(list);

    try {
      pw = new PrintWriter(file);
      for (String word : list) {
        pw.write(word + "\n");
      }
      System.out.println(list.size() + " permutations written to file: "
        + fileName);
      System.out.println();
      createdFiles.add(fileName);
      pw.close();

    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
