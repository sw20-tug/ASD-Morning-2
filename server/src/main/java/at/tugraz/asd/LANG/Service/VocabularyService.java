package at.tugraz.asd.LANG.Service;


import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Messages.in.EditVocabularyMessageIn;
import at.tugraz.asd.LANG.Model.TranslationModel;
import at.tugraz.asd.LANG.Model.VocabularyModel;
import at.tugraz.asd.LANG.Messages.in.CreateVocabularyMessageIn;
import at.tugraz.asd.LANG.Repo.TranslationRepo;
import at.tugraz.asd.LANG.Repo.VocabularyRepo;
import at.tugraz.asd.LANG.Topic;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class VocabularyService {

    @Autowired
    VocabularyRepo vocabularyRepo;
    @Autowired
    TranslationRepo translationRepo;

    public VocabularyModel saveVocabulary(CreateVocabularyMessageIn msg){
        Map<Languages, String> translations = msg.getTranslations();
        String vocab = msg.getVocabulary();

        List<TranslationModel> translationModels = new ArrayList<>();
        translations.forEach((k,v)->{
            TranslationModel translationModel = new TranslationModel(k, v);
            translationModels.add(translationModel);
            translationRepo.save(translationModel);
        });

        VocabularyModel vocabularyModel = new VocabularyModel(Topic.USER_GENERATED, vocab, translationModels);
        vocabularyRepo.save(vocabularyModel);
        return vocabularyModel;
    }
    public List<VocabularyModel> getAllVocabulary() {
        return vocabularyRepo.findAll();
    }

    public int editVocabulary(EditVocabularyMessageIn msg)
    {
        //vocabularyRepo.equals(msg.getCurrent_translations());
        AtomicInteger success = new AtomicInteger();

        msg.getCurrent_translations().values().forEach(v->{
            if(!(v.isEmpty())){
                VocabularyModel toUpdate = vocabularyRepo.findByVocabulary(v);
                if(toUpdate != null)
                {
                    List<TranslationModel> translationModels_new = new ArrayList<>();
                    msg.getNew_translations().forEach((k,val)->{
                        TranslationModel translationModel = new TranslationModel(k, val);
                        translationModels_new.add(translationModel);
                        translationRepo.save(translationModel);
                    });

                    toUpdate.getTranslationVocabMapping().clear();
                    toUpdate.setTranslationVocabMapping(translationModels_new);
                    vocabularyRepo.save(toUpdate);
                    success.set(1);
                }
            }
        });
        return success.get();
    }
}
