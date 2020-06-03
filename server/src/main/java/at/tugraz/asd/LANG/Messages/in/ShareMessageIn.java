package at.tugraz.asd.LANG.Messages.in;

import at.tugraz.asd.LANG.Model.VocabularyModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShareMessageIn
{
    private String email;
    private List<String> vocabs;
}
