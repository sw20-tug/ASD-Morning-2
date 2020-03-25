package at.tugraz.asd.LANG.Model;

import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Topic;
import lombok.Data;

import javax.persistence.*;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
public class VocabularyModel {

    @Id
    @GeneratedValue
    private UUID id;
    private Topic topic;
    private String vocabulary;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "vocab_translation_mapping",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "vocabulary_id")
    private Map<LanguageModel, String> translations;

    public VocabularyModel(Topic topic, String vocabulary, Map<LanguageModel, String> translations){
        this.topic = topic;
        this.vocabulary = vocabulary;
        this.translations = translations;
    }

}
