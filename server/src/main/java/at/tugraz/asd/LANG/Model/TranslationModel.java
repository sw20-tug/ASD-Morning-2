package at.tugraz.asd.LANG.Model;

import at.tugraz.asd.LANG.Languages;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;


@Data
@NoArgsConstructor
@Entity(name = "TranslationModel")
@Table(name = "translation_model")
@Getter
@Setter
public class TranslationModel {


    @Id
    @GeneratedValue
    private UUID id;

    Languages language;
    String vocabulary;
    public TranslationModel(Languages language_, String vocabulary_)
    {
        this.language = language_;
        this.vocabulary = vocabulary_;
    }
}
