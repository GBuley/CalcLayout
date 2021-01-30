package grant.com.myownstyle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    private MaterialTextView equation;
    private MaterialTextView calculation;
    private MaterialTextView resultText;
    private String strButton= "";
    private final char[] operations ={'+','-','/','x'};
    private final ArrayList<String> stringNums = new ArrayList<>();
    private final ArrayList<Double> nums = new ArrayList<>();
    Queue<Character> numQueue = new LinkedList<>();
    LinkedList<Character> operationQueue = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){
        }
        setContentView(R.layout.calculator_main);

        int[] ids = {R.id.percent,R.id.close_paren,R.id.open_paren,R.id.one,R.id.two,R.id.three,R.id.four,R.id.five,R.id.six,R.id.seven,R.id.eight,R.id.nine,R.id.zero,R.id.period,R.id.div,R.id.side_plus,R.id.minus, R.id.x};
        for(int id:ids){
            buttonPressed(id);
        }
        clearButtonPressed();
        equalButtonPressed(R.id.equal);

    }

    private void clearButtonPressed() {
        MaterialButton button = findViewById(R.id.clear);
        equation = findViewById(R.id.equation_text);
        resultText = findViewById(R.id.result_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText("");
                equation.setText("");
                strButton = "";
            }
        });
    }

    public void buttonPressed(int button_id){
        MaterialButton button = findViewById(button_id);
        equation = findViewById(R.id.equation_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strButton = strButton+button.getText();
                equation.setText(strButton);
            }
        });
    }

    public void equalButtonPressed(int button_id){
        MaterialButton button = findViewById(button_id);
        if(button.getText().equals("=")){
            calculation = findViewById(R.id.result_text);
            equation = findViewById(R.id.equation_text);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String result = String.valueOf(equation.getText());
                    double actualValue = getResult(result);
                    calculation.setText("= "+ actualValue);
                }
            });
        }
        else{
            System.out.println("Invalid operation");
        }
    }

    private double getResult(String result) {
        if(result.equals("")){
            return 0;
        }
        else {
            char[] resultChars = result.toCharArray();
            if(checkIfInvalidOperationUsed(resultChars)){
                return 0;
            }

            for(char c:resultChars){
                if(Character.isDigit(c)){
                    numQueue.add(c);
                }
                else{
                    listNumber(numQueue);
                    operationQueue.add(c);
                }
            }
            listNumber(numQueue);
            return equationBuilder();
        }
    }

    private double equationBuilder() {
        if(operationQueue.isEmpty()){
            listNumber(numQueue);
            return Double.parseDouble(stringNums.get(0));
        }

        double finalNumber;
        finalNumber = 0;
        for(int i=0; i<operationQueue.size();i++){
            if(i==0){
                if(operationQueue.get(0)=='+'){
                    finalNumber = Double.parseDouble(stringNums.get(i))+Double.parseDouble(stringNums.get(i+1));
                }if(operationQueue.get(0)=='/'){
                    finalNumber = Double.parseDouble(stringNums.get(i))/Double.parseDouble(stringNums.get(i+1));
                }if(operationQueue.get(0)=='X'){
                    finalNumber = Double.parseDouble(stringNums.get(i))*Double.parseDouble(stringNums.get(i+1));
                }if(operationQueue.get(0)=='-'){
                    finalNumber = Double.parseDouble(stringNums.get(i))-Double.parseDouble(stringNums.get(i+1));
                }
            }
            else{
                if(operationQueue.get(i)=='+'){
                    finalNumber += Double.parseDouble(stringNums.get(i+1));
                }if(operationQueue.get(i)=='/'){
                    finalNumber /= Double.parseDouble(stringNums.get(i+1));
                }if(operationQueue.get(i)=='X'){
                    finalNumber *= Double.parseDouble(stringNums.get(i+1));
                }if(operationQueue.get(i)=='-'){
                    finalNumber -= Double.parseDouble(stringNums.get(i+1));
                }
            }
        }
        stringNums.clear();
        operationQueue.clear();
        return finalNumber;
    }


    private void listNumber(Queue<Character> numQueue) {
        StringBuilder sb = new StringBuilder();
       for(char c:numQueue){
           sb.append(c);
       }
       stringNums.add(sb.toString());
       numQueue.clear();
    }

    private boolean checkIfInvalidOperationUsed(char[] resultChars) {
        int resultCharsLength = resultChars.length;
        for(char o:operations){
            if(resultChars[0]==o){
                return true;
            }
            if(resultChars[resultCharsLength-1] == o){
                return true;
            }
        }
        return false;
    }
}