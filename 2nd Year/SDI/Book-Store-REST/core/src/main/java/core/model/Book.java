package core.model;

import lombok.*;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Book extends BaseEntity<Long>{

    private String serialNumber;
    private String title;
    private String author;
    private int year;
    private double price;
    private int inStock;
}
