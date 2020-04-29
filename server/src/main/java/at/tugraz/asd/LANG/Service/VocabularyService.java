package at.tugraz.asd.LANG.Service;


import at.tugraz.asd.LANG.Exeptions.EditFail;
import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Messages.in.EditVocabularyMessageIn;
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
import java.util.concurrent.atomic.AtomicInteger;

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

    public List<String> getAllVocabularyOfLanguage(Languages language)
    {
        List<String> result = new ArrayList<>();
        List<VocabularyModel> all_vocab = getAllVocabulary();
        all_vocab.forEach(vm->{
            List<TranslationModel> translation_list = vm.getTranslationVocabMapping();
            translation_list.forEach(tl->{
                if (tl.getLanguage() == language)
                    result.add(tl.getVocabulary());
            });
        });
        return result;
    }

    public String getTranslation(Languages language, String word)
    {
        final String[] ret = {null};
        List<VocabularyModel> all_vocab = getAllVocabulary();
        all_vocab.forEach(vm->{
            List<TranslationModel> translation_list = vm.getTranslationVocabMapping();
            translation_list.forEach(tl->{
                if (tl.getVocabulary().equals(word)) {
                    translation_list.forEach(new_tl -> {
                        if (new_tl.getLanguage() == language)
                        {
                            ret[0] = new_tl.getVocabulary();
                        }
                    });
                }
            });
        });
        return ret[0];
    }

    public void editVocabulary(EditVocabularyMessageIn msg) throws EditFail
    {
        //TODO change so we can edit many times
        //vocabularyRepo.equals(msg.getCurrent_translations());
          AtomicInteger success = new AtomicInteger();
          success.set(0);
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
                    toUpdate.setVocabulary(msg.getNew_translations().get(Languages.DE));
                    toUpdate.setTranslationVocabMapping(translationModels_new);
                    vocabularyRepo.save(toUpdate);
                    success.set(1);
                }
            }
        });
        if(success.get() == 1)
        {
            return;
        }
        throw new EditFail();
    }
}
