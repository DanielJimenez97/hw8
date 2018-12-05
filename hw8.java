//Daniel Jimenez
//Code utilizes java classes and recursion to solve the recursion for the cracker barrel peg puzzle
import java.io.IOException;
import java.lang.*;
import java.util.*;

public class hw8{
	public static int[][] moves = new int[36][3]; //moves are defined as a 36x3 array, with 36 moves and 3 possible move parameters

	public static void main(String args[]) throws IOException{

		// 0 -17 moves are defined as in the intructors program
        	moves[0] = new int []{0,1,3};
        	moves[1] = new int []{0,2,5};
        	moves[2] = new int []{1,3,6};
        	moves[3] = new int []{1,4,8};
        	moves[4] = new int []{2,4,7};
        	moves[5] = new int []{2,5,9};
        	moves[6] = new int []{3,6,10};
        	moves[7] = new int []{3,7,12};
        	moves[8] = new int []{4,7,11};
        	moves[9] = new int []{4,8,13};
        	moves[10]= new int []{5,8,12};
        	moves[11]= new int []{5,9,14};
        	moves[12]= new int []{3,4,5};
        	moves[13]= new int []{6,7,8};
        	moves[14]= new int []{7,8,9};
        	moves[15]= new int []{10,11,12};
        	moves[16]= new int []{11,12,13};
        	moves[17]= new int []{12,13,14};

        	for(int i = 0; i < 18; i++){ //for loop sets uo the rest of the moves in the 36 array, to set the inverse moves used in the program
			moves[i+18][2] = moves[i][0];
			moves[i+18][1] = moves[i][1];
			moves[i+18][0] = moves[i][2];
        	}

        	go(0); //runs code with missing piece starting at 0, and recursion keeps it up until solution 4
	}

	public static Puzzle move(Puzzle cbPuzzle, int[] piece){
		if(cbPuzzle.peg.get(piece[0]) == 'x' && cbPuzzle.peg.get(piece[1]) == 'x' &&cbPuzzle.peg.get(piece[2]) == '.'){ // if the current spot is an x, the jumped over spot is an x, and the final spot is free

		//	System.out.println("Used move " + piece[0] + " " + piece[1] + " " + piece[2]);
			//set the first peg as visited, the jumed over peg as visited, and the final spot as taken because the peg was moved here
           		cbPuzzle.peg.set(piece[0], '.');
           		cbPuzzle.peg.set(piece[1], '.');
           		cbPuzzle.peg.set(piece[2], 'x');
           		cbPuzzle.piecesLeft = cbPuzzle.piecesLeft - 1; //one piece less is used so we decrement
           		cbPuzzle.changed = true; //if the cbPuzzle has been change we set its use variable to true

	        	return cbPuzzle; //return the object of the puzzle
        	}
        	else{
           		cbPuzzle.changed = false; //if the if statement fails, means that the ouzzle object was not edited
           		return cbPuzzle; //return the puzzle object
        	}
    	}

    	public static void solve(Puzzle cbPuzzle){
        	for(int i = 0; i < moves.length; i++){ // for the length of the moves in the moves array

            		Puzzle cbPuzzleNew = new Puzzle(cbPuzzle); //creates new instance of puzzle board object with old puzzle object

            		int[] temp = moves[i]; //temp is an array that holds a row of info, specificially the i row, of the moves
            		cbPuzzleNew = move(cbPuzzleNew, temp); //the new puzzle tries to execute the moves on it and if it wors then its object is edited
            		if(cbPuzzleNew.changed){ //if the object is changed is a true on the pobject
                		//System.out.println("Used move " + temp[0] + " " + temp[1] + " " + temp[2]);
				cbPuzzleNew.moveHistory.add(temp); //final solution gets the moves set from the change
                		if(cbPuzzleNew.piecesLeft < 2){ //if pieces left is less than 2, then it means theres one piece left so solution is solved
                    			finalSolve(cbPuzzleNew); // so final solution function is called
                		}
               			solve(cbPuzzleNew); //otherwise solve us called again!!
            		}
        	}
    	}

    	public static void finalSolve(Puzzle cbPuzzle){ // final solve is the last call to the solve function

		for(int i = 0; i < 15; i++){ //this for loop resets the peg board object to all x's
                        cbPuzzle.peg.set(i, 'x');
                }
                cbPuzzle.peg.set(cbPuzzle.piece, '.'); // then the pieceby which the final peg is on is placed
       		cbPuzzle.print(); //print the start board
        	for(int i = 0; i <cbPuzzle.moveHistory.size(); i++){ // for loop prints through the solution history
			int[] temp = cbPuzzle.moveHistory.get(i);
           		cbPuzzle = move(cbPuzzle,cbPuzzle.moveHistory.get(i));
           		cbPuzzle.print();
			System.out.println("Used move " + temp[0] + " " + temp[1] + " " + temp[2]);
        	}
        	go(cbPuzzle.piece + 1); //recursive  call to the go function as in teacher solution, and it uses the current piece + 1
    	}

    	public static void go(int start){

        	if(start > 4){ // if the start variable passed in greater than 4, then exit the program

            		System.exit(1); //end the program
        	}
        	System.out.println("finalSolve " + start); //otherwise print the iteration of the program

        	Puzzle cbPuzzle = new Puzzle(); //new board for the puzzle is created an object

                for(int i = 0; i < 15; i++){ //puzzle is intialized to all x's
                        cbPuzzle.peg.add(i, 'x'); //piece in that spot
                }
                cbPuzzle.peg.set(start, '.'); //except for the start block which uses the start param to set the start peg

                cbPuzzle.piecesLeft = 14; //all new puzzles start with 14 pieces
                cbPuzzle.piece = start; // the start location is saved in a variable for later use

		solve(cbPuzzle); // first call to the solve function
    	}
}

class Puzzle {
        public ArrayList<int[]> moveHistory = new ArrayList<int[]>(); //contains history of the board
	public ArrayList<Character> peg = new ArrayList<Character>(); //arraylist of the pegs in the puzzle
    	public boolean changed = false; //member var keeps track of board changed
    	public int piece; // piece is the start point member var of propgram
        public int piecesLeft; // pieces left is member variable to say how many pieces have been removed from code

        public Puzzle(){ //default constructor
        }

        public void print(){ // print function
                System.out.println("     " + peg.get(0));
                System.out.println("    " + peg.get(1) + " " + peg.get(2));
                System.out.println("   " + peg.get(3) + " " + peg.get(4) + " " + peg.get(5));
                System.out.println("  " + peg.get(6) + " " + peg.get(7) + " " + peg.get(8) + " " + peg.get(9));
                System.out.println(" " + peg.get(10) + " " + peg.get(11) + " " + peg.get(12) + " " + peg.get(13) + " " + peg.get(14));
        }


    	public Puzzle(Puzzle cbPuzzle){
		peg.addAll(cbPuzzle.peg);
        	moveHistory.addAll(cbPuzzle.moveHistory); //copies all history of the pgm

        	piecesLeft = cbPuzzle.piecesLeft;
        	piece = cbPuzzle.piece;
        	changed = false;
    	}


}
