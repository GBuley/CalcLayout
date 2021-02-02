package view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

import grant.com.myownstyle.R;
import viewModel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MaterialTextView equation;
    private MaterialTextView calculation;
    private MaterialTextView resultText;
    private String strButton= "";
    private MainViewModel viewModel;
    private final char[] operations ={'+','-','/','X'};
    private final ArrayList<String> stringNums = new ArrayList<>();
//    private final ArrayList<Double> nums = new ArrayList<>();
    private LinkedList<Character> numQueue = new LinkedList<>();
    private LinkedList<Character> operationQueue = new LinkedList<>();


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

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getResult().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double result) {
                resultText.setText(String.format((Locale.getDefault()), result.toString()));
            }
        });
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
                    viewModel.findResult(result);

                }
            });
        }
        else{
            System.out.println("Invalid operation");
        }
    }



        //        int count =0;
        //Trying to add pemdas
//        ArrayList<Integer> pemdasPlaces = new ArrayList<Integer>();
//        for(char o:operationQueue){
//            if(o=='X'||o=='/'){
//                pemdasPlaces.add(count);
//            }
//            count++;
//        }
//
//        if(!pemdasPlaces.isEmpty()){
//            int pemCount=0;
//            for(int i:pemdasPlaces){
//                if(operationQueue.get(i)=='X'){
//                    stringNums.add(i, String.valueOf(Double.parseDouble(stringNums.get(i))*Double.parseDouble(stringNums.get(i+1))));
//                    stringNums.add(i+1,"");
//                    operationQueue.add(i,' ');
//                }else if(operationQueue.get(i)=='/'){
//                    finalNumber = Double.parseDouble(stringNums.get(i))/Double.parseDouble(stringNums.get(i+1));
//                    operationQueue.add(i,' ');
//                }
//            }
//        }


}