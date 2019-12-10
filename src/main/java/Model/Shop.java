package Model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Shop implements Entity {
    private String name;
    private String type;
    @Override
    public String toString(){
        return name;
    }
    @Override
    public String toFileString(String separator){
        return name+separator+type;
    }
    public Shop (String[] params){
        this.name = params[0];
        this.type = params[1];
    }
}
