package com.augsmachado.lotteryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    private TextView mFirstNumber, mSecondNumber, mThirdNumber, mFourthNumber, mFifthNumber;
    private Button mGenerateButton, mResetButton;

    private String[] lotteryBalls = {
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        bindViews();

        // Initialization of buttons
        final Button mGenerateButton = (Button) findViewById(R.id.generateButton);
        final Button mResetButton = (Button) findViewById(R.id.resetButton);

        // Set initial visibility of app's components
        mGenerateButton.setVisibility(View.VISIBLE);
        mResetButton.setVisibility(View.GONE);

        mFirstNumber.setVisibility(View.INVISIBLE);
        mSecondNumber.setVisibility(View.INVISIBLE);
        mThirdNumber.setVisibility(View.INVISIBLE);
        mFourthNumber.setVisibility(View.INVISIBLE);
        mFifthNumber.setVisibility(View.INVISIBLE);




        // Buttons
        mGenerateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mGenerateButton.setVisibility(View.GONE);
                mResetButton.setVisibility(View.VISIBLE);

                // Generate the numbers of raffle
                raffle();

                // Show the raffle
                mFirstNumber.setVisibility(View.VISIBLE);
                mSecondNumber.setVisibility(View.VISIBLE);
                mThirdNumber.setVisibility(View.VISIBLE);
                mFourthNumber.setVisibility(View.VISIBLE);
                mFifthNumber.setVisibility(View.VISIBLE);
            }
        });


        mResetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mGenerateButton.setVisibility(View.VISIBLE);
                mResetButton.setVisibility(View.GONE);

                // Hide the numbers that was reset
                mFirstNumber.setVisibility(View.INVISIBLE);
                mSecondNumber.setVisibility(View.INVISIBLE);
                mThirdNumber.setVisibility(View.INVISIBLE);
                mFourthNumber.setVisibility(View.INVISIBLE);
                mFifthNumber.setVisibility(View.INVISIBLE);

                // Reset the raffle
                mFirstNumber.setText("01");
                mSecondNumber.setText("02");
                mThirdNumber.setText("03");
                mFourthNumber.setText("04");
                mFifthNumber.setText("05");
            }
        });

    }

    private void raffle() {
        String randomNumber[] = new String[5];

        // Generate random integers in range 0 to 49 => 50 elements
        int pos[] = generateRandomNumbers();

        // Add numbers generated in each position based in lotteryBalls list
        for(int i = 0; i < 5; i++) {
            randomNumber[i] = lotteryBalls[pos[i]];
        }

        mFirstNumber.setText(randomNumber[0]);
        mSecondNumber.setText(randomNumber[1]);
        mThirdNumber.setText(randomNumber[2]);
        mFourthNumber.setText(randomNumber[3]);
        mFifthNumber.setText(randomNumber[4]);
    }

    private int[] generateRandomNumbers() {
        int array[] = new int[5];

        // Generate an array with 5 positions with numbers between 0 and 49
        for (int i=0; i < 5; i++){
            array[i] = ThreadLocalRandom.current().nextInt(49);
        }

        // Order by array on asc
        Arrays.sort(array);

        // Verify if numbers are uniques
        for (int i = 0; i < 5; i++){
            for(int j = i+1; j < 5; j++) {

                // If number not unique, generate another number
                if (array[i] == array[j]){
                    array[j] = ThreadLocalRandom.current().nextInt(49);
                }
            }
        }

        // Order by array on asc
        Arrays.sort(array);

        return array;
    }




    private void bindViews() {
        mFirstNumber = (TextView) findViewById(R.id.firstNumber);
        mSecondNumber = (TextView) findViewById(R.id.secondNumber);
        mThirdNumber = (TextView) findViewById(R.id.thirdNumber);
        mFourthNumber = (TextView) findViewById(R.id.fourthNumber);
        mFifthNumber = (TextView) findViewById(R.id.fifthNumber);

        mGenerateButton = (Button) findViewById(R.id.generateButton);
    }
}