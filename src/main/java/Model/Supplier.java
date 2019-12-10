package Model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier implements Entity {
    private String type;
    private String name;
    @Override
    public String toString(){
        return type;
    }
    @Override
    public String toFileString(String separator){
        return type+separator+name;
    }
    public Supplier(String[] params){
        this.type = params[0];
        this.name = params[1];
    }
}
