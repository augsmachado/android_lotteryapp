package com.augsmachado.lotteryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    private TextView mFirstNumber, mSecondNumber, mThirdNumber, mFourthNumber, mFifthNumber;
    private TextView mNumbersDrawn, mResult, mRaffleHits;
    private EditText mFirstBallInsert, mSecondBallInsert, mThirdBallInsert, mFourthBallInsert, mFifthBallInsert;
    private ImageView mWin;
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
        mWin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mWin.setVisibility(View.INVISIBLE);
            }
        });

        mGenerateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (validateInput() == true){
                    // Generate the numbers of raffle
                    raffle();

                    mGenerateButton.setVisibility(View.GONE);
                    mResetButton.setVisibility(View.VISIBLE);

                    mNumbersDrawn.setVisibility(View.VISIBLE);

                    // Show the raffle
                    mFirstNumber.setVisibility(View.VISIBLE);
                    mSecondNumber.setVisibility(View.VISIBLE);
                    mThirdNumber.setVisibility(View.VISIBLE);
                    mFourthNumber.setVisibility(View.VISIBLE);
                    mFifthNumber.setVisibility(View.VISIBLE);

                    // Show hits
                    isMatchOnRaffle();
                    mResult.setVisibility(View.VISIBLE);
                    mRaffleHits.setVisibility(View.VISIBLE);
                }
            }
        });


        mResetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mGenerateButton.setVisibility(View.VISIBLE);
                mResetButton.setVisibility(View.GONE);

                // Hide image win
                mWin.setVisibility(View.INVISIBLE);

                // Hide the numbers that was reset
                mFirstNumber.setVisibility(View.INVISIBLE);
                mSecondNumber.setVisibility(View.INVISIBLE);
                mThirdNumber.setVisibility(View.INVISIBLE);
                mFourthNumber.setVisibility(View.INVISIBLE);
                mFifthNumber.setVisibility(View.INVISIBLE);

                // Hide results
                mNumbersDrawn.setVisibility(View.INVISIBLE);
                mResult.setVisibility(View.INVISIBLE);
                mRaffleHits.setVisibility(View.INVISIBLE);

                // Reset the inserted numbers
                mFirstBallInsert.setText("");
                mSecondBallInsert.setText("");
                mThirdBallInsert.setText("");
                mFourthBallInsert.setText("");
                mFifthBallInsert.setText("");


                // Reset the raffle
                String str;
                str = getResources().getString(R.string._01);
                mFirstNumber.setText(str);
                str = getResources().getString(R.string._02);
                mSecondNumber.setText(str);
                str = getResources().getString(R.string._03);
                mThirdNumber.setText(str);
                str = getResources().getString(R.string._04);
                mFourthNumber.setText(str);
                str = getResources().getString(R.string._05);
                mFifthNumber.setText(str);

                // Reset colors of generated numbers
                mFirstNumber.setTextColor(getResources().getColor(R.color.black));
                mSecondNumber.setTextColor(getResources().getColor(R.color.black));
                mThirdNumber.setTextColor(getResources().getColor(R.color.black));
                mFourthNumber.setTextColor(getResources().getColor(R.color.black));
                mFifthNumber.setTextColor(getResources().getColor(R.color.black));

            }
        });

    }

    private boolean validateInput() {
        int duration = Toast.LENGTH_SHORT;

        int array[] = new int[5];
        String str[] = new String[5];

        Context context = getApplicationContext();
        CharSequence isRange = getResources().getString(R.string.isRange);
        CharSequence isDifferent = getResources().getString(R.string.isDifferent);
        CharSequence isEmpty = getResources().getString(R.string.isEmpty);

        str[0] = mFirstBallInsert.getText().toString();
        str[1] = mSecondBallInsert.getText().toString();
        str[2] = mThirdBallInsert.getText().toString();
        str[3] = mFourthBallInsert.getText().toString();
        str[4] = mFifthBallInsert.getText().toString();

        // Validate if the input is not empty
        for(int i = 0; i < 5; i++) {
            if (str[i].length() == 0) {
                Toast.makeText(context, isEmpty, duration).show();
                return false;
            }
        }

        // Populate and order by array on asc
        for(int i = 0; i < 5; i++) array[i] = Integer.parseInt(str[i]);
        Arrays.sort(array);


        // Validate if number is between 1 to 50
        for(int i = 0; i < 5; i++) {
            if(array[i] <= 0 || array[i] > 50) {
                Toast.makeText(context, isRange, duration).show();
                return false;
            }
        }

        // Validate if numbers are different
        for(int i = 0; i < 5; i++) {
            for (int j = i+1; j < 5; j++) {
                if(array[i] == array[j]) {
                    Toast.makeText(context, isDifferent, duration).show();
                    return false;
                }
            }
        }

        // If pass in all validation
        return true;
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

    public void isMatchOnRaffle() {
        String inserted[] = new String[5];
        String generated[] = new String[5];
        int sum = 0;

        inserted[0] = mFirstBallInsert.getText().toString();
        inserted[1] = mSecondBallInsert.getText().toString();
        inserted[2] = mThirdBallInsert.getText().toString();
        inserted[3] = mFourthBallInsert.getText().toString();
        inserted[4] = mFifthBallInsert.getText().toString();

        generated[0] = mFirstNumber.getText().toString();
        generated[1] = mSecondNumber.getText().toString();
        generated[2] = mThirdNumber.getText().toString();
        generated[3] = mFourthNumber.getText().toString();
        generated[4] = mFifthNumber.getText().toString();

        // Count hits
        for (int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++) {
                if (generated[i].equals(inserted[j])) {
                    sum++;

                    // Change color of number generated if match with the number inserted
                    switch (i) {
                        case 0:
                            mFirstNumber.setTextColor(getResources().getColor(R.color.teal_700));
                            break;
                        case 1:
                            mSecondNumber.setTextColor(getResources().getColor(R.color.teal_700));
                            break;
                        case 2:
                            mThirdNumber.setTextColor(getResources().getColor(R.color.teal_700));
                            break;
                        case 3:
                            mFourthNumber.setTextColor(getResources().getColor(R.color.teal_700));
                            break;
                        case 4:
                            mFifthNumber.setTextColor(getResources().getColor(R.color.teal_700));
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        // Set text hits
        String hits;
        switch (sum) {
            case 0:
                hits = getResources().getString(R.string.hit_00);
                mRaffleHits.setText(hits);
                break;
            case 1:
                hits = getResources().getString(R.string.hit_01);
                mRaffleHits.setText(hits);
                break;
            case 2:
                hits = getResources().getString(R.string.hit_02);
                mRaffleHits.setText(hits);
                break;
            case 3:
                hits = getResources().getString(R.string.hit_03);
                mRaffleHits.setText(hits);
                break;
            case 4:
                hits = getResources().getString(R.string.hit_04);
                mRaffleHits.setText(hits);
                break;
            case 5:
                hits = getResources().getString(R.string.hit_05);
                mRaffleHits.setText(hits);

                mWin.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    private void bindViews() {
        // Initialize the components in view
        mFirstBallInsert = (EditText) findViewById(R.id.firstBallInsert);
        mSecondBallInsert = (EditText) findViewById(R.id.secondBallInsert);
        mThirdBallInsert = (EditText) findViewById(R.id.thirdBallInsert);
        mFourthBallInsert = (EditText) findViewById(R.id.fourthBallInsert);
        mFifthBallInsert = (EditText) findViewById(R.id.fifthBallInsert);

        mGenerateButton = (Button) findViewById(R.id.generateButton);

        mNumbersDrawn = (TextView) findViewById(R.id.numbersDrawnText);
        mResult = (TextView) findViewById(R.id.resultText);
        mRaffleHits = (TextView) findViewById(R.id.raffleHits);

        mWin = (ImageView) findViewById(R.id.win);

        mNumbersDrawn.setVisibility(View.INVISIBLE);
        mResult.setVisibility(View.INVISIBLE);
        mRaffleHits.setVisibility(View.INVISIBLE);
        mWin.setVisibility(View.INVISIBLE);


        mFirstNumber = (TextView) findViewById(R.id.firstNumber);
        mSecondNumber = (TextView) findViewById(R.id.secondNumber);
        mThirdNumber = (TextView) findViewById(R.id.thirdNumber);
        mFourthNumber = (TextView) findViewById(R.id.fourthNumber);
        mFifthNumber = (TextView) findViewById(R.id.fifthNumber);
    }
}