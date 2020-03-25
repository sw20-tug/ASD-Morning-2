package at.tugraz.asd.LANG.Service;


import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Model.LanguageModel;
import at.tugraz.asd.LANG.Model.VocabularyModel;
import at.tugraz.asd.LANG.Messages.in.CreateVocabularyMessageIn;
import at.tugraz.asd.LANG.Repo.LanguagesRepository;
import at.tugraz.asd.LANG.Repo.VocabularyRepo;
import at.tugraz.asd.LANG.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VocabularyService {

    @Autowired
    VocabularyRepo vocabularyRepo;
    @Autowired
    LanguagesRepository languagesRepository;

    public VocabularyModel saveVocabulary(CreateVocabularyMessageIn msg){
        Map<Languages,String> translations = msg.getTranslations();
        Map<LanguageModel,String> transaltions_db = new HashMap<>();

        //THIS WILL NOT WORK ...
        translations.forEach((k,v)->{
           List<LanguageModel> lm = languagesRepository.findAll();
           lm.forEach((el)->  transaltions_db.put(el,v));

        });
        return vocabularyRepo.save(new VocabularyModel(Topic.USER_GENERATED,msg.getVocabulary(),transaltions_db));

    }
    public List<VocabularyModel> getAllVocabulary() {
        return vocabularyRepo.findAll();
    }
}
