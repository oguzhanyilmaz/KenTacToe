/*
 * This file is part of KenTacToe, (c) Kenshin Himura, 2013.
 * 
 * KenTacToe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * KenTacToe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with KenTacToe.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.tictactoe.game;

import java.util.Scanner;

import com.tictactoe.player.*;
import com.tictactoe.search.*;
import com.tictactoe.table.*;
import com.tictactoe.move.*;

/**
 * The <code>Game</code> class is designed to be a class which holds any type of game,
 * but in this program, it is defined as if it is a tic tac toe game by default,
 * for convenience. Thus, it has a table called gameGrid, and two players, and a
 * reference to the current player of the game. It has a status indicator for whether
 * the game is over. Also, a search is there, for use by the AI. (Later, we may add a
 * moves list to store the different moves made in the right order, for review or
 * other purposes.)
 * @author Kenshin Himura
 *
 */
public class Game
{
	/**
	 * Stores the current table of the game.
	 * Primarily used for displaying, processing and passing as argument to the
	 * various methods the <code>Game</code> class can make use of.
	 */
	private Table gameGrid;
	/**
	 * Stores the first <code>Player</code>.
	 */
	private Player playerOne;
	/**
	 * Stores the second <code>Player</code>.
	 */
	private Player playerTwo;
	/**
	 * Is a reference to the current player, and shifts between playerOne and
	 * playerTwo between turns.
	 */
	private Player currentPlayer;
	/**
	 * An object of the <code>Search</code> class, which can be used by the AI.
	 */
	private Search currentSearch;
	/**
	 * A status indicator of type <code>boolean</code> used to indicate if the gameOver
	 * conditions have been met. Primarily used to end the game.
	 */
	private boolean gameOver=false;
	/**
	 * A byte to denote the fixed search depth of the game
	 * in half-plies (half-moves).
	 * Initialized to '-1' to mean value not set.
	 */
	private byte searchDepth=-1;
	/**
	 * Default constructor of the <code>Game</code> class.
	 * Creates a 3x3 Table for use with the game and a two players,
	 * one of type "User" with name "UserPlayer" and another of Type AI.
	 */
	public Game()
	{
		gameGrid=new Table((byte)3,(byte)3);
		playerOne=new Player("UserPlayer");
		playerTwo=new Player();
		currentSearch=new Search();
	}
	/**
	 * Same as the default constructor, except that this constructor allows
	 * for the use of varying grid size of the table used in the game.
	 * @param gridSize Size of the grid used in the game (no of rows (or)
	 * no of columns (ie) both must be equal).
	 */
	public Game(byte gridSize)
	{
		gameGrid=new Table(gridSize,gridSize);
		playerOne=new Player("UserPlayer");
		playerTwo=new Player();
		currentSearch=new Search();
	}
	/**
	 * This is the constructor to be called in the main function of the program,
	 * if the grid size is more than 3x3.
	 * It takes in two parameters to personalize the game more effectively
	 * by taking a parameter for the name of the player and a grid size.
	 * @param gridSize Size of the grid used in the game (no of rows (or)
	 * no of columns (ie) both must be equal).
	 * @param userName Name of the Player
	 */
	public Game(byte gridSize,String userName)
	{
		gameGrid=new Table(gridSize,gridSize);
		playerOne=new Player(userName);
		playerTwo=new Player();
		currentSearch=new Search();
	}
	/**
	 * This is the constructor to be called in the main function of the program,
	 * if the grid size can be chosen, and when the player wants to be able to
	 * choose to be the first or the second player.
	 * It takes in three parameters to personalize the game more effectively
	 * by taking a parameter for the name of the player and a grid size
	 * and a byte to decide whether the user needs to be player one or two.
	 * @param gridSize Size of the grid used in the game (no of rows (or)
	 * no of columns (ie) both must be equal).
	 * @param userName Name of the Player
	 * @param turn Number representing the turn of the player (1 means play as
	 * X, any other value is taken to mean play as O)
	 */
	public Game(byte gridSize,String userName,byte turn)
	{
		gameGrid=new Table(gridSize,gridSize);
		if(turn==1)
		{
			playerOne=new Player(userName);
			playerTwo=new Player();
		}
		else
		{
			playerOne=new Player();
			playerTwo=new Player(userName);
		}
		currentSearch=new Search();
	}
	/**
	 * This is the constructor to be called in the main function of the program,
	 * if the grid size can be chosen, and when the player wants to be able to
	 * choose to be the first or the second player.
	 * It takes in three parameters to personalize the game more effectively
	 * by taking a parameter for the name of the player and a grid size
	 * and a byte to decide whether the user needs to be player one or two.
	 * @param gridSize Size of the grid used in the game (no of rows (or)
	 * no of columns (ie) both must be equal).
	 * @param userName Name of the Player
	 * @param turn Number representing the turn of the player (1 means play as
	 * X, any other value is taken to mean play as O)
	 * @param searchDepth depth of search necessary, should be non-zero and
	 * less than the number of cells in the generated table.
	 */
	public Game(byte gridSize,String userName,byte turn, byte searchDepth)
	{
		gameGrid=new Table(gridSize,gridSize);
		if(turn==1)
		{
			playerOne=new Player(userName);
			playerTwo=new Player();
		}
		else
		{
			playerOne=new Player();
			playerTwo=new Player(userName);
		}
		currentSearch=new Search();
		if((searchDepth>0)&&(searchDepth<=(gridSize*gridSize)))	
			setSearchDepth(searchDepth);
	}
	/**
	 * This is the constructor of game used when two human players
	 * should play.
	 * @param gridSize Size of the grid to be used in the game.
	 * @param userOneName Name of the first player.
	 * @param userTwoName Name of the second player.
	 */
	public Game(byte gridSize, String userOneName, String userTwoName)
	{
		gameGrid=new Table(gridSize,gridSize);
		playerOne=new Player(userOneName);
		playerTwo=new Player(userTwoName);
		currentSearch=new Search();
	}
	/**
	 * Generic setter method of the searchDepth variable. Defined as good
	 * programming practice, to access private members of a class.
	 * @param searchDepth depth of serach, fixed, in half-plies (half-moves)
	 */
	public void setSearchDepth(byte searchDepth) {
		this.searchDepth = searchDepth;
	}
	/**
	 * This is the constructor called in the main function of the program
	 * if a 3x3 grid is to e used by default.
	 * It takes in a string parameter to personalize the game more effectively
	 * by taking a parameter for the name of the player. Creates a 3x3 grid.
	 * @param userName Name of the Player.
	 */
	public Game(String userName)
	{
		gameGrid=new Table((byte)3,(byte)3);
		playerOne=new Player(userName);
		playerTwo=new Player();
		currentSearch=new Search();
	}
	/**
	 * Initialization method of the <code>Game</code> class.
	 * Initially, simply used as a debug-friendly method to know if things were
	 * working. Now, its the start of the program once it receives enough
	 * parameters for creating the game. It tells the sign of the players and
	 * their respective names. Also sets the reference of current player to
	 * player one.
	 */
	public void init()
	{
		System.out.println("Game Initialized...");
		gameGrid.printTable();
		System.out.println(playerOne.getPlayerName()+" "+playerOne.getPlayerSign());
		System.out.println(playerTwo.getPlayerName()+" "+playerTwo.getPlayerSign());
		currentPlayer=playerOne;
	}
	/**
	 * This method is used to get inputs for the table from both players.
	 * As of now, this program supports only one user player. If the player
	 * is of type AI, then the search object is used, else the player enters
	 * the index of the box he wants to sign in.
	 */
	public void getInput()
	{
		byte index=gameGrid.getFirstDashIndex();
		Move moveToMake;
		@SuppressWarnings("resource")
		Scanner inputStream=new Scanner(System.in);
		if(currentPlayer.getPlayerType()=="AI")
		{
			if(searchDepth!=-1)
				currentSearch=new Search(gameGrid,currentPlayer,searchDepth);
			else
				currentSearch=new Search(gameGrid,currentPlayer);
			moveToMake=currentSearch.getBestMove();
			gameGrid=gameGrid.makeMove(moveToMake);
		}
		else
		{
			do{
				do{
					if((index<0)||(index>gameGrid.getSizeOfTable()))
						System.out.println("Please enter valid values for index (0-"+gameGrid.getSizeOfTable()+").");
					if(!gameGrid.isEmpty((byte)(index-1)))
						System.out.println("Please enter the index of an empty box.");
					System.out.println("Enter the index of the box you want to sign, "+currentPlayer.getPlayerName()+":");
					index=inputStream.nextByte();
				}while((index<0)||(index>gameGrid.getSizeOfTable()));
			}while(!gameGrid.isEmpty((byte)(index-1)));
			gameGrid.updateTable((byte)(index-1), currentPlayer.getPlayerSign());
		}
		if(currentPlayer==playerOne)
			currentPlayer=playerTwo;
		else
			currentPlayer=playerOne;
		System.out.println("Grid updated...");
		gameGrid.printTable();
		if(gameGrid.isComplete()!=-1)
			gameOver=true;
	}
	/**
	 * This is the method of the game called by the main function of the program.
	 * It just initializes the game, gets the values from both players and keeps
	 * updating the grid until the game is over.
	 */
	public void play()
	{
		init();
		for(int i=0;((gameOver==false)&&(i<gameGrid.getSizeOfTable()));i++)
			getInput();
		if(gameGrid.isComplete()==1)
			System.out.println(playerOne.getPlayerName()+
					"("+playerOne.getPlayerType()+") won!");
		else if(gameGrid.isComplete()==0)
			System.out.println(playerTwo.getPlayerName()+
					"("+playerTwo.getPlayerType()+") won!");
		else
			System.out.println("Game drawn!");
	}
}