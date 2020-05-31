package at.tugraz.asd.LANG.Model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Clob;
import java.util.Date;
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

    private String userName;

    @Lob
    private Clob testStates;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public TestModel(String userName, Clob testStates) {
        this.userName = userName;
        this.testStates = testStates;
    }
}
