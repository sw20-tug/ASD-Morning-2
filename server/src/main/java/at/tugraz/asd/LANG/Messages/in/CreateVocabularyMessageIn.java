package at.tugraz.asd.LANG.Messages.in;

import at.tugraz.asd.LANG.Languages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateVocabularyMessageIn {
    String vocabulary;
    Map<Languages,String> translations;

}

