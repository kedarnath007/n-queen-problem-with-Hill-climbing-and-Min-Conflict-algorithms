/****************************************************/
// Filename: NQueenProblem.java
// Change history:
// 10.27.2017 / Kedarnath
/****************************************************/

/* This class solve the N Queens problem by both Hill Climbing and Min-Conflict methods.
 1. The n-board is represented as an array of n elements which store the row number there the queen is placed.
 2. Everytime when there is no solution found, a random board is generated.
 3. Iterations need to be mentioned for Min-Conflicts algorithm*/
/****************************************************/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
public class NQueenProblem {
	//This method calculates all the row conflicts for a queen placed in a particular cell.
	public static int rowCollisions(int a[],int n)
	{
		int collisions=0;
		for(int i=0;i<n;i++)
		{
			for (int j=0;j<n;j++)
			{
				if(i!=j)
				{
					if (a[i]==a[j]) 	//Calculates whether the queen is in the same row.
						collisions++;
				}
			}
		}
		return collisions;
	}
	//This method calculates all the diagonal conflicts for a particular position of the queen
	public static int diagonalCollisions(int a[],int n)
	{
		int collisions=0,d=0;
		for(int i=0;i<n;i++)
		{
			for (int j=0;j<n;j++)
			{
				if(i!=j)
				{
					d=Math.abs(i-j);
					if((a[i]==a[j]+d)||(a[i]==a[j]-d)) 	//This verifies whether there are any diagonal collisions
						collisions++;
				}
			}
		}
		return collisions;
	}
	//This method returns total number of collisions for a particular queen position
	public static int totalCollisions(int a[],int n)
	{
		int collisions=0;
		collisions= rowCollisions(a,n) + diagonalCollisions(a,n);
		return collisions;
	}
	/*This method calculates the conflicts for the current state of the board and quits whenever finds a better state.
	 Note: This function is used for Hill Climbing algorithm*/
	public static boolean bestSolution(int a[],int n)
	{
		int min,collisions=0,row=-1,col=-1,m;
		boolean checkBetter=false;
		int[] best;
		//Sets min variable to the collisions of current board so that if finds better than this it will quit.
		min = totalCollisions(a,n);	 
		best = Arrays.copyOf(a, n); //Create a duplicate array for handling different operations
		for (int i=0;i<n;i++) 		//This iteration is for each column
		{
			if(checkBetter)			//If it finds and better state than the current, it will quit
				break;
			m = best[i];
			for (int j=0;j<n;j++)	//This iteration is for each row in the selected column
			{
				if(j!=m)			//This condition ensures that, current queen position is not taken into consideration.
				{
					best[i]=j;		//Assigning the queen to each position and then calculating the collisions
					collisions=totalCollisions(best,n);
					if(min>collisions)	//If a better state is found, that particular column and row values are stored
					{
							col=i;
							row=j;
							min=collisions;
							checkBetter=true;
							break;
					}
				}
			best[i]=m;		//Restoring the array to the current board position
			}
		}
		if(col==-1||row==-1)	//If there is no better state found
		{
			System.out.println("Reached Local Maxima with "+collisions+" Regenerating randomly");
			return false;
		}
		a[col]=row;		
		return true;		//Returns true to the main function if there is any better state found
	}
	//Below function generates a random state of the board
	public static void randomGenerate(int [] a,int n)
	{
		Random gen = new Random();
		for(int i = 0; i < n; i++) 
		  a[i] = gen.nextInt(n)+0;
	}
	//Below function verifies whether the current state of the board is the solution(I.e with zero conflicts)
	public static boolean isSolution(int a[],int n)
	{
		if(totalCollisions(a,n)==0)
			return true;
		return false;
	}
	//Below method finds the solution for the n-queens problem with Min-Conflicts algorithm
	public static void minConflict(int b[],int n, int iterations)
	{
		//This array list is for storing the columns from which a random column will be selected
		ArrayList<Integer> store = new ArrayList<Integer>();	
		fillList(store,n);
		Random gen = new Random();
		int randomSelection,currentValue,randomValue;
		int col,randomCount=0,movesTotal=0,movesSolution=0,row=0,maxSteps;
		maxSteps=iterations;	//The maximum steps that can be allowed to find a solution with this algorithm
		while(!isSolution(b,n))	//Loops until it finds a solution
		{
			randomSelection = gen.nextInt(store.size())+0;	//Randomly selects a column from the available 
			currentValue=b[store.get(randomSelection)];		//This stores the current queue position in the randomly selected column
			randomValue=store.get(randomSelection);			
			int min = collisionsMinConflict(b,n,randomValue);//Sets the minimum variable to the current queue collisions
			int min_compare=min;
			store.remove(randomSelection);
			for (int i=0;i<n;i++)
			{
				if(currentValue!=i)
				{
					b[randomValue]=i;
					col=collisionsMinConflict(b,n,randomValue);	//Calculates the collisions of the queen at particular position
					if(col<min)	
					{
						min=col;
						row=i;
					}
				}
			}
			if(min_compare==min)	//When there is no queen with minimum conflicts than the current position
			{
				if(maxSteps!=0)		//Checks if the maximum steps is reached
				{
					if(store.size()>0)	//checks whether there are columns available in the Array List
					{
						b[randomValue]=currentValue;	//restores the queen back to the previous position
						maxSteps--;					
					}
					else
					{
						fillList(store,n);
					}
				}
				else	//If the max steps is reached then, the board is regenerated and initiated the max steps variable
				{
					randomCount++;
					movesSolution=0;
					randomGenerate(b,n);
					fillList(store,n);
					maxSteps=iterations;
				}
			}
			else		//When we find the the position in the column with minimum conflicts
			{
				movesTotal++;
				movesSolution++;
				b[randomValue]=row;
				min_compare=min;
				store.clear();
				maxSteps--;
				fillList(store,n);
			}
		}
		System.out.println();
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(j==b[i])
					System.out.print("Q ");
				else
					System.out.print("X ");
			}
			System.out.println();
		}
		System.out.println("Total number of Random Restarts: "+randomCount);
		System.out.println("Total number of Moves: "+movesTotal);
		System.out.println("Number of Moves in the solution set: "+movesSolution);
	}
	//Below function returns the collisions of a queen in a particular column of the board
	public static int collisionsMinConflict(int[] b,int n,int index)
	{
		int collisions=0,t=0;
		for(int i=0;i<n;i++)
		{
			if(i!=index)
			{
				t=Math.abs(index-i);
				if(b[i]==b[index])
					collisions++;
				else if(b[index]==b[i]+t||b[index]==b[i]-t)
					collisions++;
			}
		}
		return collisions;
	}
	//Below function fills the Array List with numbers 0 to n-1
	public static void fillList(ArrayList<Integer> store,int n)
	{
		for(int i=0;i<n;i++)
		{
			store.add(i);
		}
		return;
	}
	//Main function
	public static void main(String[] args) 
	{
		int a[],b[];
		int n,totalRestart=0,movesTotal=0,movesSolution=0,choice;
		System.out.println("Please enter the value of n:");
		Scanner sc=new Scanner(System.in);
		n = sc.nextInt();
		if((n>1&&n<4)||n<1)
		{
			System.out.println("*Please choose n value either greater than 3 or equals to 1 - Program Terminated");
			return;
		}
		if(n==1)
		{
			System.out.println("There is no choice of algorithm for this value of 'n':");
			System.out.println("Q");
			return;
		}
		System.out.println("Please select one from the below options:");
		System.out.println("1. Solve n queens with Hill Climbing and Random Restart");
		System.out.println("2. Min Conflict method with random restart");
		System.out.println("3. Both methods and their results");
		choice = sc.nextInt();
		if(choice<1||choice>3)
		{
			System.out.println("*Program terminated - Wrong option selected");
			return;
		}
		a = new int[n];
		b = new int[n];
		randomGenerate(a,n);	//Randomly generate the board
		b = Arrays.copyOf(a, n);
		//The below code will be executed if the user chooses he options 1 or 3(n-queen with Hill Climbing method)
		if(choice==1||choice==3)
		{
			System.out.println("**********Hill Climbing with Random Restart*********");
			long startTime=System.currentTimeMillis();
			while(!isSolution(a,n))		//Executes until a solution is found
			{
				if(bestSolution(a,n))	//If a better state for a board is found
				{
					movesTotal++;
					movesSolution++;
					continue;
				}
				else					//If a better state is not found
				{
					movesSolution=0;
					randomGenerate(a,n);	//Board is generated Randomly
					totalRestart++;
				}
			}
			long endTime=System.currentTimeMillis();
			for(int i=0;i<n;i++)
			{
				for(int j=0;j<n;j++)
				{
					if(j==a[i])
						System.out.print("Q ");
					else
						System.out.print("X ");
				}
				System.out.println();
			}
			System.out.println("Number of Restarts: "+totalRestart);
			System.out.println("Total number of moves taken: "+movesTotal);	//Gives the total number of moves from starting point
			System.out.println("Number of moves in the solution set: "+movesSolution); //Gives number of steps in the solution set.
			System.out.println("Time Taken in milli seconds: "+(endTime-startTime));
		}
		//If the Min-Conflict algorithm is selected
		if(choice==2||choice==3)
		{
			int iterations=0;
			System.out.println();
			System.out.println("*******Min Conflict With Random Restart*******");
			System.out.println("Please enter the maximum number of steps for iteration:");
			iterations = sc.nextInt();
			sc.close();
			long startTime=System.currentTimeMillis();
			minConflict(b,n,iterations);
			long endTime=System.currentTimeMillis();
			System.out.println("Time Taken in milli seconds: "+(endTime-startTime));
		}
	}

}
