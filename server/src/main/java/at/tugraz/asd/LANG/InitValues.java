package at.tugraz.asd.LANG;

import at.tugraz.asd.LANG.Messages.in.CreateVocabularyMessageIn;
import at.tugraz.asd.LANG.Service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;


@Component
public class InitValues implements ApplicationRunner {
    @Autowired
    VocabularyService service;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        HashMap<Languages, String> translation_1 = new HashMap<>();
        translation_1.put(Languages.DE, "haus");
        translation_1.put(Languages.FR, "maison");
        translation_1.put(Languages.EN, "house");
        CreateVocabularyMessageIn msg = new CreateVocabularyMessageIn("haus", translation_1);

        service.saveVocabulary(msg);


        HashMap<Languages, String> translation_2 = new HashMap<>();
        translation_2.put(Languages.DE, "kind");
        translation_2.put(Languages.FR, "lekind");
        translation_2.put(Languages.EN, "child");
        msg = new CreateVocabularyMessageIn("kind", translation_2);
        service.saveVocabulary(msg);

        HashMap<Languages, String> translation_3 = new HashMap<>();
        translation_3.put(Languages.DE, "brot");
        translation_3.put(Languages.FR, "pagutte");
        translation_3.put(Languages.EN, "breat");
        msg = new CreateVocabularyMessageIn("brot", translation_3);

        service.saveVocabulary(msg);

        HashMap<Languages, String> translation_4 = new HashMap<>();
        translation_4.put(Languages.DE, "uhr");
        translation_4.put(Languages.FR, "leuhr");
        translation_4.put(Languages.EN, "watch");
        msg = new CreateVocabularyMessageIn("uhr", translation_4);

        service.saveVocabulary(msg);

        HashMap<Languages, String> translation_5 = new HashMap<>();
        translation_5.put(Languages.DE, "hunger");
        translation_5.put(Languages.FR, "unger");
        translation_5.put(Languages.EN, "hungry");
        msg = new CreateVocabularyMessageIn("hunger", translation_5);
        service.saveVocabulary(msg);
    }
}