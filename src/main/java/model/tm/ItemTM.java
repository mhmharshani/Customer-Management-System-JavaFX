package model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemTM {
    private String code;
    private String description;
    private String size;
    private Double price;
    private Integer qtyOnHand;

}
