package viewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import grant.com.myownstyle.R;

public class MainViewModel extends AndroidViewModel {
    private static final String STRING_DATA_KEY = "STRING_DATA_KEY";

    private LinkedList<Character> numQueue = new LinkedList<>();
    private LinkedList<Character> operationQueue = new LinkedList<>();
    private MutableLiveData<Double> result = new MutableLiveData<>();
    private MutableLiveData<String> strEquation = new MutableLiveData<>();
    private final char[] operations ={'+','-','/','X'};
    private final ArrayList<String> stringNums = new ArrayList<>();

    private final SharedPreferences preferences = getApplication().getSharedPreferences(getApplication().getString(R.string.result_string), Context.MODE_PRIVATE);

    public MainViewModel(@NonNull Application application){ super(application);}

    public void saveResult(String data){
        preferences.edit().putString(STRING_DATA_KEY, data).apply();
    }

    public String getStringDataKey(){
        return preferences.getString(STRING_DATA_KEY, " ");
    }


    public LiveData<Double> getResult() {
        return result;
    }

    public LiveData<String> getStrEquation(){
        return strEquation;
    }

    public void setStrEquation(String str){
        strEquation.setValue(str);
    }



    public void findResult(String resultString) {
        if (resultString.equals("")) {
            result.setValue(0.0);
        } else {
            char[] resultChars = resultString.toCharArray();
            if (checkIfInvalidOperationUsed(resultChars)) {
                result.setValue(0.0);
            }

            for (char c : resultChars) {
                if (Character.isDigit(c)) {
                    numQueue.add(c);
                } else if (c == '.') {
                    numQueue.add(c);
                } else if (c == '%') {
                    if (numQueue.size() == 1) {
                        numQueue.addFirst('0');
                        numQueue.addFirst('.');
                    } else if (numQueue.size() == 2) {
                        numQueue.addFirst('.');
                    } else {
                        int queueSize = numQueue.size();
                        numQueue.add(queueSize - 2, '.');
                    }
                } else if (c == operations[1] && numQueue.size() == 0) {
                    numQueue.add(c);
                } else {
                    listNumber(numQueue);
                    operationQueue.add(c);
                }
            }
            listNumber(numQueue);
            result.setValue(equationBuilder());
            stringNums.clear();
            operationQueue.clear();
        }

    }

    private void listNumber(Queue<Character> numQueue) {
        StringBuilder sb = new StringBuilder();
        for(char c:numQueue){
            sb.append(c);
        }
        stringNums.add(sb.toString());
        numQueue.clear();
    }

    private double equationBuilder() {
        if (operationQueue.isEmpty()) {
            return Double.parseDouble(stringNums.get(0));
        }

        double finalNumber = 0;

        for(int i=0; i<operationQueue.size();i++){
            if(i==0){
                if(operationQueue.get(0)==operations[0]){
                    finalNumber = Double.parseDouble(stringNums.get(i))+Double.parseDouble(stringNums.get(i+1));
                }else if(operationQueue.get(0)==operations[2]){
                    finalNumber = Double.parseDouble(stringNums.get(i))/Double.parseDouble(stringNums.get(i+1));
                }else if(operationQueue.get(0)==operations[3]){
                    finalNumber = Double.parseDouble(stringNums.get(i))*Double.parseDouble(stringNums.get(i+1));
                }else if(operationQueue.get(0)==operations[1]){
                    finalNumber = Double.parseDouble(stringNums.get(i))-Double.parseDouble(stringNums.get(i+1));
                }
            }
            else{
                if(operationQueue.get(i)==operations[0]){
                    finalNumber += Double.parseDouble(stringNums.get(i+1));
                }else if(operationQueue.get(i)==operations[2]){
                    finalNumber /= Double.parseDouble(stringNums.get(i+1));
                }else if(operationQueue.get(i)==operations[3]){
                    finalNumber *= Double.parseDouble(stringNums.get(i+1));
                }else if(operationQueue.get(i)==operations[1]){
                    finalNumber -= Double.parseDouble(stringNums.get(i+1));
                }
            }
        }
        return finalNumber;
    }

    private boolean checkIfInvalidOperationUsed(char[] resultChars) {
        int resultCharsLength = resultChars.length;
        for(char o:operations){
            if(resultChars[0]==operations[1]){
                return false;
            }
            else if(resultChars[0]==o){
                return true;
            }
            else if(resultChars[resultCharsLength-1] == o){
                return true;
            }
        }
        return false;
    }
}
