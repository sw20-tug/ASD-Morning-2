package at.tugraz.asd.LANG.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "TestModel")
@Table(name = "test_model")
@Getter
@Setter
public class TestModel {
    @Id
    @GeneratedValue
    private UUID id;

    // ...
}
