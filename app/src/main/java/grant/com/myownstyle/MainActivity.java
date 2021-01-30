package grant.com.myownstyle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {

    private MaterialTextView equation;
    private MaterialTextView calculation;
    private String strButton= "";
    private char[] nums ={0,1,2,3,4,5,6,7,8,9};
    private char[] operations ={'+','-','/','x'};


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
                    double actualValue = parseValue(result);
                    calculation.setText("= "+ result);
                }
            });
        }
        else{
            System.out.println("Invalid operation");
        }
    }

    private double parseValue(String result) {
        if(result.equals("")){
            return 0;
        }
        else {
            char[] resultChars = result.toCharArray();
            int resultCharsLength = resultChars.length;
            for(char o:operations){
                if(resultChars[0]==o){
                    return 0;
                }
                if(resultChars[resultCharsLength] == o){
                    return 0;
                }
            }
            for(char c:resultChars){
                switch (c){
                    case 1:

                }
            }
        }
            return 0;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.calculator_main);

        int[] ids = {R.id.percent,R.id.close_paren,R.id.open_paren,R.id.one,R.id.two,R.id.three,R.id.four,R.id.five,R.id.six,R.id.seven,R.id.eight,R.id.nine,R.id.zero,R.id.plus,R.id.period,R.id.div,R.id.side_plus,R.id.minus};
        for(int id:ids){
            buttonPressed(id);
        }

        equalButtonPressed(R.id.equal);

    }
}