package Controller;

import Util.DbWork;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QueryToolController implements Initializable {

    private DbWork dbWork = DbWork.getInstance();

    @FXML
    private TextArea resultArea;
    @FXML
    private TextField queryField;
    @FXML
    private void runQuery(){
        String sql = queryField.getText();
        String type = sql.split(" ")[0].toLowerCase();
        resultArea.clear();
        switch (type){
            case "select":
            case "select*":
            case "select(":{
                resultArea.setText("select query completed: \n");
                List<String> resultList = runSelect(sql);
                for(String result: resultList){
                    resultArea.setText(resultArea.getText()+result);
                    resultArea.setText(resultArea.getText()+"\n");
                }
                break;
            }
            default:{
                if(runOther(sql))
                resultArea.setText(type+" query completed.");
                break;
            }
        }

    }

    private boolean runOther(String sql){
        try{
            Statement statement = dbWork.getConnection().createStatement();
            statement.execute(sql);
            return true;
        }
        catch (SQLException e){
            resultArea.setText(e.getLocalizedMessage());
            e.printStackTrace();
            return false;
        }
    }

    private List<String> runSelect(String sql){
        List<String> result = new ArrayList<>();
        try{
            Statement statement = dbWork.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            List<Integer> lengths = new ArrayList<>();
            for(int i=1; i<resultSetMetaData.getColumnCount()+1; i++){
                Integer length = resultSetMetaData.getColumnName(i).length();
                resultSet.beforeFirst();
                while (resultSet.next()){
                    if(resultSet.getString(i).length()>length)
                        length=resultSet.getString(i).length();
                }
                lengths.add(length);
            }
            String line = "";
            for(int i=1; i<resultSetMetaData.getColumnCount()+1; i++){
                String columnName = " " + resultSetMetaData.getColumnName(i);
                while(columnName.length()<lengths.get(i-1)+1){
                    columnName+=" ";
                }
                if(i!=resultSetMetaData.getColumnCount())
                columnName+=" |";
                line+=columnName;
            }
            result.add(line);
            line="";
            int j=0;
            for(Integer len: lengths){
                for(int i=0;i<len+2;i++){
                    line += "-";
                }
                if(j++ != lengths.size()-1){
                    line +="+";
                }

            }
            result.add(line);
            resultSet.beforeFirst();
            int size=0;
            while (resultSet.next()){
                size++;
                line = "";
                for(int i=1; i<resultSetMetaData.getColumnCount()+1; i++){
                    String string = " "+resultSet.getString(i);
                    while(string.length()<lengths.get(i-1)+1){
                        string+=" ";
                    }
                    line +=string;
                    if(i!=resultSetMetaData.getColumnCount()){
                        line+=" |";
                    }
                }
                result.add(line);
            }
            line = "("+size+" row";
            if(size==1){
                line += ")";
            }
            else{
                line += "s)";
            }
            result.add(line);
        }
        catch (SQLException e){
            resultArea.setText(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbWork = MainframeController.getDbWork();
    }
}
