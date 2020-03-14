INTRODUCTION:

This is a small script to produce common variants of a particular password to be used when testing login credentials using tools such as BurpSuite Intruder or similar. This is useful when a simple password may have been used, but has been made more complex owing to applications commonly enforcing the use of passwords that are longer, have capital letter, numbers symbols etc. 
The list of permutations produced of a particular word is not meant to be exhaustive, but merely to show the most common iterations of a particular word that has been made more complex. Analysis of huge numbers of existing passwords have shown that when a user has to choose a complex password they sometimes use their existing (simple) password but change it using predictable methods. This gives them the best balance of meeting the application's complexity requirements whilst still being able to easily remember their password. For instance if the password `cisco' is chosen, then it would be common for this to be changed to e.g. Cisco1 or c1sco or cisc0 or cisco99 to make it more complex. 

Unlike other password-guessing tools, the lists produced - because they are using permutations which have a higher likelihood of success - are much shorter, which means a password-guessing test will be finished between a few seconds to a few hours depending on the word chosen and application to be tested. The lists can be ported to any tool and the running/configuration of this script is extremely quick and simple.   

Once a word is entered in the program, the user can choose one of 5 options. Option 1 being the simplest/shortest and Option 5 being the most comprehensive/longest:

Option 1 will produce a very short list of the following permutations of a word:

- doubling the word eg testtest
- adding 1,2,3,4,9 separately and together before and after the word e.g. test9, 12test, 1234test
- substituting the letters for symbols eg `i' for `1' and `!' ,  `a' for `@' and `h' for `#'.
- substituting `o' for `0', and `e' for `3'
- adding `?', `!', `-' and `*' before and after the word eg !test, test*
- capitalising various letters e.g. Test, TEST, TesT


Option 2 will generate a longer list of permutations which do all the variations of option 1), for instance, for the word `house' there would be: #0USE! , !HoUs3, -h0usE etc

Option 3 will generate a longer list which includes everything from options 1) and 2) but also adds the characters `.’, `£', `$' and `%' to the front and then at the end of each word.

Option 4 generates a list which includes everything in option 1 and 2, but with the following additions to the basic permutations as listed in option 1):

 - Adds the numbers 1940 through to 2019 to the beginning and end of the basic word e.g. 1955test
 - Adds the numbers 00 through to 99 at the beginning and end of the basic word e.g.  test67

Option 5 produces the longest list incorporating everything from options 1, 2, 3 and 4.

All of the options takes only a few seconds to generate a list. The longest list (using a 9-character word) produced by option 5,  will consist of around 20,000 - 36000 words. This is reduced to around 3500-14000 words if a 4-character word is used. The shortest list (using a 4 character word) produced by option 1 will only be 20-20 words in length.


REQUIREMENTS:

The ability to run Java on the local machine.
Optional: A free or paid version of BurpSuite installed.


USAGE INSTRUCTIONS: 

Download the cpp.class and cpp.java files from this repo and save to a folder on the local machine. Open a command line and go to the directory of these saved files or use a program that can run Java files. Type:

java cpp

Then enter the word to be changed. It can consist of any characters, capital letters, symbols, numbers etc, but has to be between 4 and 9 characters (inclusive) in length. Once a word has been entered, you will be asked which option you want from 1 to 5. As mentioned above, Option 1 will be the shortest, simplest list, presenting the most common permutations of the entered word. Option 2 will produce longer list as it has more permutations and so on until option 5 which has the longest list of permutations.

The final generated list of permutations will be saved as a .txt file in the same directory as the program files. 

This list can be copied & pasted into BurpSuite Intruder for password-guessing tests or used in other similar tools.

