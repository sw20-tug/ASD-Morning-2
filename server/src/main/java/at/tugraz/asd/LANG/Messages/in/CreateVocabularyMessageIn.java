package at.tugraz.asd.LANG.Messages.in;

import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class CreateVocabularyMessageIn {
    String vocabulary;
    Topic topic;
    Map<Languages,String> translations;
}

