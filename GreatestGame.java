package com.aca;
import java.util.Random;
import java.util.Scanner;

public class GreatestGame
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        int repeat;
        int totalWins = 0;
        int totalLosses = 0;


        intro();

        do
        {
            int randNumber = randNum();
            int guess = getChosenNumber(randNumber);
            validation(guess, randNumber);
            System.out.println("\n\n\nChosen Number: " + guess + "\n" + "Random Number: " + randNumber + "\n");
            stats(totalWins, totalLosses, guess, randNumber);

            System.out.println("Would you like to play again? <1 for yes; 0 for no>: ");
            repeat = input.nextInt();
        }while(repeat == 1);
    }

    private static void intro()
    {
        System.out.print("\n===============================================");
        System.out.println("\nWelcome to The Greatest Number Guessing game!");
        System.out.println("----------------------------------------------");
        System.out.print("");
        System.out.println("I am going to think of a number between 1-100...");
        System.out.println("Can you guess it?\n\n");
    }

    private static int getChosenNumber(int randomNumber)
    {
        Scanner input = new Scanner(System.in);
        int guess = 0;


        boolean correctAnswer;

        for(int i = 1; i < 5; i++)
        {
            System.out.print("Guess " + i + ": ");
            guess = input.nextInt();
            correctAnswer = validation(guess, randomNumber);

            if(correctAnswer)
            {
                break;
            }
        }
        return guess;
    }

    private static int randNum()
    {
        Random random = new Random();

        int randomNumber = random.nextInt(101);

        return randomNumber;
    }

    private static boolean validation(int guess, int randomNumber)
    {
        if(guess < randomNumber)
        {
            System.out.println("Low.");
        }
        else if(guess > randomNumber)
        {
            System.out.println("High.");
        }
        else if(guess == randomNumber)
        {
            return true;
        }
        return false;
    }

    private static boolean stats(int totalWins, int totalLosses, int guess, int randomNumber)
    {
        boolean winOrLoss = validation(guess, randomNumber);
        if(winOrLoss == true)
        {
            System.out.println("YOU WON.");
        }
        else
        {
            System.out.println("YOU LOST.");
        }
        return false;
    }


}
