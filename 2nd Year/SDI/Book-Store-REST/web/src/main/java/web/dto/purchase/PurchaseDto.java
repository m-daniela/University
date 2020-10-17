package web.dto.purchase;

import lombok.*;
import web.dto.BaseDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class PurchaseDto extends BaseDto {
    private Long clientID;
    private Long bookID;
    private int nrBooks;
}
