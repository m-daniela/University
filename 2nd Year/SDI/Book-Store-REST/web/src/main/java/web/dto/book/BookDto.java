package web.dto.book;

import lombok.*;
import web.dto.BaseDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class BookDto extends BaseDto {
    private String serialNumber;
    private String title;
    private String author;
    private int year;
    private double price;
    private int inStock;
}
