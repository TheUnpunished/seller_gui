package Model;

import lombok.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Registration implements Entity{
    private String name;
    private Date date;
    private int count;
    @Override
    public String toString(){
        return this.name;
    }
    @Override
    public String toFileString(String separator){
        return name+separator+date.toString()+separator+count;
    }
    public Registration(String[] params){
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try{
            this.name = params[0];
            this.date = new Date(simpleDateFormat.parse(params[1]).getTime());
            this.count = Integer.parseInt(params[2]);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
    }
}
