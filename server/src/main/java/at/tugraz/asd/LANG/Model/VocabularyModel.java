package at.tugraz.asd.LANG.Model;

import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Topic;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Data
@NoArgsConstructor
@Entity(name = "VocabularyModel")
@Table(name = "vocabulary_model")
@Getter
@Setter

public class VocabularyModel {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Topic topic;

    private String vocabulary;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<TranslationModel> TranslationVocabMapping = new ArrayList<>();




    public VocabularyModel(Topic topic, String vocabulary, List<TranslationModel> translations)
    {
        this.topic = topic;
        this.vocabulary = vocabulary;
        this.TranslationVocabMapping = translations;
    }
}
