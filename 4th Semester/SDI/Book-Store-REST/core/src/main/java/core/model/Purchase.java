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
public class Purchase extends BaseEntity<Long> {
    private Long clientID;
    private Long bookID;
    private int nrBooks;
}
