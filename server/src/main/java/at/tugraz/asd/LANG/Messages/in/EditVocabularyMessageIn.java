package at.tugraz.asd.LANG.Messages.in;

import at.tugraz.asd.LANG.Languages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditVocabularyMessageIn
{
    Map<Languages,String> current_translations;
    Map<Languages,String> new_translations;
    Integer rating;
}

