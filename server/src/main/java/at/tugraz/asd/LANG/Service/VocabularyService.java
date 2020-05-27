package at.tugraz.asd.LANG.Service;


import at.tugraz.asd.LANG.Exeptions.EditFail;
import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Messages.in.EditVocabularyMessageIn;
import at.tugraz.asd.LANG.Messages.out.GetAllTopicsOut;
import at.tugraz.asd.LANG.Messages.out.VocabularyOut;
import at.tugraz.asd.LANG.Model.TranslationModel;
import at.tugraz.asd.LANG.Model.VocabularyModel;
import at.tugraz.asd.LANG.Messages.in.CreateVocabularyMessageIn;
import at.tugraz.asd.LANG.Repo.TranslationRepo;
import at.tugraz.asd.LANG.Repo.VocabularyRepo;
import at.tugraz.asd.LANG.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VocabularyService {

    @Autowired
    VocabularyRepo vocabularyRepo;
    @Autowired
    TranslationRepo translationRepo;

    public VocabularyModel saveVocabulary(CreateVocabularyMessageIn msg){
        Map<Languages, String> translations = msg.getTranslations();
        String vocabulary = msg.getVocabulary();
        Topic topic = msg.getTopic();

        List<TranslationModel> translationModels = new ArrayList<>();
        translations.forEach((k,v)->{
            TranslationModel translationModel = new TranslationModel(k, v);
            translationModels.add(translationModel);
            translationRepo.save(translationModel);
        });

        VocabularyModel vocabularyModel = new VocabularyModel(topic, vocabulary, translationModels, Integer.valueOf(0));
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

    public VocabularyModel editVocabulary(EditVocabularyMessageIn msg) throws EditFail {
        //TODO change so we can edit many times
        //vocabularyRepo.equals(msg.getCurrent_translations());

        for(var v : msg.getCurrent_translations().values()) {
            if (!(v.isEmpty())) {
                VocabularyModel toUpdate = vocabularyRepo.findByVocabulary(v);
                if (toUpdate != null) {
                    List<TranslationModel> translationModels_new = new ArrayList<>();
                    msg.getNew_translations().forEach((k, val) -> {
                        TranslationModel translationModel = new TranslationModel(k, val);
                        translationModels_new.add(translationModel);
                        translationRepo.save(translationModel);
                    });
                    toUpdate.getTranslationVocabMapping().clear();
                    toUpdate.setVocabulary(msg.getNew_translations().get(Languages.DE));
                    toUpdate.setTranslationVocabMapping(translationModels_new);
                    toUpdate.setRating(msg.getRating());
                    return vocabularyRepo.save(toUpdate);
                }
            }
        }
        throw new EditFail();
    }

    public List<VocabularyModel> sortVocabStudyInterface(Languages language, String aOrz) {
        ArrayList<VocabularyOut> ret = new ArrayList<>();
        List<VocabularyModel> vocab = getAllVocabulary();
        if(vocab.isEmpty())
            return vocab;

        if(aOrz.equals("a"))
        {
            vocab.sort(new Comparator<VocabularyModel>() {
                @Override
                public int compare(VocabularyModel vocabularyModel, VocabularyModel t1) {
                    String compare1 = vocabularyModel.getTranslationVocabMapping().get(0).getVocabulary();
                    String compare2 = vocabularyModel.getTranslationVocabMapping().get(0).getVocabulary();

                    for(int i = 0; i < vocabularyModel.getTranslationVocabMapping().size(); i++)
                    {
                        if(vocabularyModel.getTranslationVocabMapping().get(i).getLanguage() == language)
                        {
                            compare1 = vocabularyModel.getTranslationVocabMapping().get(i).getVocabulary();
                        }
                        if(t1.getTranslationVocabMapping().get(i).getLanguage() == language)
                        {
                            compare2 = t1.getTranslationVocabMapping().get(i).getVocabulary();
                        }
                    }
                    return compare1.compareTo(compare2);
                }
            });
        }
        if(aOrz.equals("z"))
        {
            vocab.sort(new Comparator<VocabularyModel>() {
                @Override
                public int compare(VocabularyModel vocabularyModel, VocabularyModel t1) {
                    String compare1 = vocabularyModel.getTranslationVocabMapping().get(0).getVocabulary();
                    String compare2 = vocabularyModel.getTranslationVocabMapping().get(0).getVocabulary();

                    for(int i = 0; i < vocabularyModel.getTranslationVocabMapping().size(); i++)
                    {
                        if(vocabularyModel.getTranslationVocabMapping().get(i).getLanguage() == language)
                        {
                            compare1 = vocabularyModel.getTranslationVocabMapping().get(i).getVocabulary();
                        }
                        if(t1.getTranslationVocabMapping().get(i).getLanguage() == language)
                        {
                            compare2 = t1.getTranslationVocabMapping().get(i).getVocabulary();
                        }
                    }
                    return compare2.compareTo(compare1);
                }
            });
        }
        return vocab;
    }

    public List<VocabularyModel> sortVocabOverview(String aOrz) {
        ArrayList<VocabularyOut> ret = new ArrayList<>();
        List<VocabularyModel> vocab = getAllVocabulary();
        if(vocab.isEmpty())
            return vocab;
        if(aOrz.equals("a"))
        {
            vocab.sort(new Comparator<VocabularyModel>() {
                @Override
                public int compare(VocabularyModel vocabularyModel, VocabularyModel t1) {
                    return vocabularyModel.getVocabulary().compareTo(t1.getVocabulary());
                }
            });
        }
        if(aOrz.equals("z"))
        {
            vocab.sort(new Comparator<VocabularyModel>() {
                @Override
                public int compare(VocabularyModel vocabularyModel, VocabularyModel t1) {
                    return t1.getVocabulary().compareTo(vocabularyModel.getVocabulary());
                }
            });
        }
        return vocab;
    }

    public GetAllTopicsOut getAllTopics()
    {
        Topic[] topics = Topic.class.getEnumConstants();
        List<Topic> list = Arrays.asList(topics);
        GetAllTopicsOut ret = new GetAllTopicsOut(list);
        return ret;
    }

    public List<VocabularyModel> sortTopics(Topic msg)
    {
        return vocabularyRepo.findByTopic(msg);
    }
}
