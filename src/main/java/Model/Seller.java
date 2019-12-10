package Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Seller implements Entity{
    private String name;
    private String city;
    private String product_name;
    private int price;
    private int count;

    @Override
    public String toString(){
        return name+" - "+city+" - "+product_name;
    }
    @Override
    public String toFileString(String separator){
        return this.name+separator+this.city+separator+this.product_name+separator+
                this.price+separator+this.count;
    }
    public Seller(String[] params){
        this.name = params[0];
        this.city = params[1];
        this.product_name = params[2];
        this.price = Integer.parseInt(params[3]);
        this.count = Integer.parseInt(params[4]);
    }
}
