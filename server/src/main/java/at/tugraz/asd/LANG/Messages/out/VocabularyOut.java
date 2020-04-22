package at.tugraz.asd.LANG.Messages.out;

import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyOut {
    private Topic topic;
    private String vocabulary;
    private Map<Languages, String> translations;
}
