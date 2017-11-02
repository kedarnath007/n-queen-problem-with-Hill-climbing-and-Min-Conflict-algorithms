--------------------
Compiler
--------------------

javac           (compiler)

---------------------------------
Command Line Execution
---------------------------------
	  javac NQueenProblem.java 	(Compiling)

	  java NQueenProblem		(Running)

---------------------------------
Input/Output
---------------------------------

Input: The value for 'n'. (The input can be any number ranging from 4 to any value.)
The choice of algorithm to be chosen.

Output: The output consist the below set of results
1. The title of the algorithm
2. All the set of row numbers for each column will be diaplayed in each line.
3. Total number of moves until it reaches the solution set.
4. No. of rows in the final state to reach the solution state.
5. Total number of Random restarts.
6. Total number of iterations(For min conflict algorithm)

------------------------------
Steps for running the program
------------------------------

1. Input the vale of n. The value of n should be greater than 3 or equal to 1 (program will be terminated if other number are given)
2. Input the choice if the algorithm to be used for getting the solution. (Both of the algorithms also can be chosen)
3. For Min-Conflict algorithm, user need to provide the maximum number of steps.

------------------
Sample Output 
------------------
Please enter the value of n:
10
Please select one from the below options:
1. Solve n queens with Hill Climbing and Random Restart
2. Min Conflict method with random restart
3. Both methods and their results
3
**********Hill Climbing with Random Restart*********
Reached Local Maxima with 6 Regenerating randomly
Reached Local Maxima with 4 Regenerating randomly
Reached Local Maxima with 8 Regenerating randomly
Reached Local Maxima with 6 Regenerating randomly
X X X X Q X X X X X 
X X Q X X X X X X X 
X X X X X X X X X Q 
X X X X X Q X X X X 
X Q X X X X X X X X 
X X X X X X X X Q X 
X X X X X X Q X X X 
X X X Q X X X X X X 
X X X X X X X Q X X 
Q X X X X X X X X X 
Number of Restarts: 4
Total number of moves taken: 43
Number of moves in the solution set: 12
Time Taken in milli seconds: 7

*******Min Conflict With Random Restart*******
Please enter the maximum number of steps for iteration:
1000

X X Q X X X X X X X 
X X X X X X X X X Q 
X X X X X Q X X X X 
X Q X X X X X X X X 
X X X X X X X X Q X 
X X X X X X Q X X X 
X X X Q X X X X X X 
X X X X X X X Q X X 
Q X X X X X X X X X 
X X X X Q X X X X X 
Total number of Random Restarts: 1
Total number of Moves: 108
Number of Moves in the solution set: 27
Time Taken in milli seconds: 9

