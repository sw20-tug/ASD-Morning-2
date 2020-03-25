package at.tugraz.asd.LANG.Model;

import at.tugraz.asd.LANG.Languages;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

public class LanguageModel {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Languages language;
}
