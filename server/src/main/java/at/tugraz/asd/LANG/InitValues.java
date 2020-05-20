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
        HashMap<Languages, String> translationMap = new HashMap<>();
        translationMap.put(Languages.DE, "haus");
        translationMap.put(Languages.FR, "maison");
        translationMap.put(Languages.EN, "house");
        CreateVocabularyMessageIn msg = new CreateVocabularyMessageIn("haus",Topic.Home, translationMap);
        service.saveVocabulary(msg);

        translationMap.clear();
        translationMap.put(Languages.DE, "Tee");
        translationMap.put(Languages.FR, "thé");
        translationMap.put(Languages.EN, "tea");
        msg = new CreateVocabularyMessageIn("Tee", Topic.Food, translationMap);
        service.saveVocabulary(msg);

        translationMap.clear();
        translationMap.put(Languages.DE, "Tastatur");
        translationMap.put(Languages.FR, "clavier");
        translationMap.put(Languages.EN, "keyboard");
        msg = new CreateVocabularyMessageIn("Tastatur", Topic.Electronic, translationMap);
        service.saveVocabulary(msg);

        translationMap.clear();
        translationMap.put(Languages.DE, "Saft");
        translationMap.put(Languages.FR, "suc");
        translationMap.put(Languages.EN, "juice");
        msg = new CreateVocabularyMessageIn("Saft", Topic.Food ,translationMap);
        service.saveVocabulary(msg);

        translationMap.clear();
        translationMap.put(Languages.DE, "Protein");
        translationMap.put(Languages.FR, "protein");
        translationMap.put(Languages.EN, "protéine");
        msg = new CreateVocabularyMessageIn("Protein",Topic.Food ,translationMap);
        service.saveVocabulary(msg);

        translationMap.clear();
        translationMap.put(Languages.DE, "kind");
        translationMap.put(Languages.FR, "lekind");
        translationMap.put(Languages.EN, "child");
        msg = new CreateVocabularyMessageIn("kind", Topic.Human ,translationMap);
        service.saveVocabulary(msg);

        translationMap.clear();
        translationMap.put(Languages.DE, "brot");
        translationMap.put(Languages.FR, "pagutte");
        translationMap.put(Languages.EN, "breat");
        msg = new CreateVocabularyMessageIn("brot",Topic.Food ,translationMap);
        service.saveVocabulary(msg);

        translationMap.clear();
        translationMap.put(Languages.DE, "uhr");
        translationMap.put(Languages.FR, "leuhr");
        translationMap.put(Languages.EN, "watch");
        msg = new CreateVocabularyMessageIn("uhr", Topic.Home ,translationMap);
        service.saveVocabulary(msg);

        translationMap.clear();
        translationMap.put(Languages.DE, "hunger");
        translationMap.put(Languages.FR, "unger");
        translationMap.put(Languages.EN, "hungry");
        msg = new CreateVocabularyMessageIn("hunger",Topic.Food ,translationMap);
        service.saveVocabulary(msg);

        translationMap.clear();
        translationMap.put(Languages.DE, "Kühlschrank");
        translationMap.put(Languages.FR, "frigo");
        translationMap.put(Languages.EN, "fridge");
        msg = new CreateVocabularyMessageIn("Kühlschrank", Topic.Home ,translationMap);
        service.saveVocabulary(msg);

        translationMap.clear();
        translationMap.put(Languages.DE, "Apfel");
        translationMap.put(Languages.FR, "Pomme");
        translationMap.put(Languages.EN, "apple");
        msg = new CreateVocabularyMessageIn("Apfel", Topic.Food ,translationMap);
        service.saveVocabulary(msg);

        translationMap.clear();
        translationMap.put(Languages.DE, "Birne");
        translationMap.put(Languages.FR, "Poire");
        translationMap.put(Languages.EN, "pear");
        msg = new CreateVocabularyMessageIn("Birne", Topic.Food ,translationMap);
        service.saveVocabulary(msg);

        translationMap.clear();
        translationMap.put(Languages.DE, "Gelb");
        translationMap.put(Languages.FR, "Jaune");
        translationMap.put(Languages.EN, "yellow");
        msg = new CreateVocabularyMessageIn("Gelb", Topic.DEFAULT,translationMap);
        service.saveVocabulary(msg);
    }
}