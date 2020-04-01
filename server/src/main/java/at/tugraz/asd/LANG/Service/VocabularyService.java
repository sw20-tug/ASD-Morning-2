package at.tugraz.asd.LANG.Service;


import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Model.TranslationModel;
import at.tugraz.asd.LANG.Model.VocabularyModel;
import at.tugraz.asd.LANG.Messages.in.CreateVocabularyMessageIn;
import at.tugraz.asd.LANG.Repo.TranslationRepo;
import at.tugraz.asd.LANG.Repo.VocabularyRepo;
import at.tugraz.asd.LANG.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VocabularyService {

    @Autowired
    VocabularyRepo vocabularyRepo;
    @Autowired
    TranslationRepo translationRepo;

    public VocabularyModel saveVocabulary(CreateVocabularyMessageIn msg){
        Map<Languages, String> translations = msg.getTranslations();
        String vocabulary = msg.getVocabulary();

        List<TranslationModel> translationModels = new ArrayList<>();
        translations.forEach((k,v)->{
            TranslationModel translationModel = new TranslationModel(k, v);
            translationModels.add(translationModel);
            translationRepo.save(translationModel);
        });

        VocabularyModel vocabularyModel = new VocabularyModel(Topic.USER_GENERATED, vocabulary, translationModels);
        vocabularyRepo.save(vocabularyModel);
        return vocabularyModel;
    }
    public List<VocabularyModel> getAllVocabulary() {
        return vocabularyRepo.findAll();
    }
}
