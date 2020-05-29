package at.tugraz.asd.LANG.Service;


import at.tugraz.asd.LANG.Exeptions.CreateVocabularyFail;
import at.tugraz.asd.LANG.Exeptions.EditFail;
import at.tugraz.asd.LANG.Exeptions.RatingFail;
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
import com.google.gson.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class VocabularyService {

    @Autowired
    VocabularyRepo vocabularyRepo;
    @Autowired
    TranslationRepo translationRepo;

    public VocabularyModel saveVocabulary(CreateVocabularyMessageIn msg) throws CreateVocabularyFail {
        Map<Languages, String> translations = msg.getTranslations();
        String vocabulary = msg.getVocabulary();
        Topic topic = msg.getTopic();
        if(translations == null || vocabulary == null || topic == null)
            throw new CreateVocabularyFail();

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

    public List<VocabularyModel> sortRating(String aOrz) {
        ArrayList<VocabularyOut> ret = new ArrayList<>();
        List<VocabularyModel> vocab = getAllVocabulary();
        if(vocab.isEmpty())
            return vocab;
        if(aOrz.equals("c"))
        {
            vocab.sort(new Comparator<VocabularyModel>() {
                @Override
                public int compare(VocabularyModel vocabularyModel, VocabularyModel t1) {
                    return vocabularyModel.getRating().compareTo(t1.getRating());
                }
            });
        }
        if(aOrz.equals("d"))
        {
            vocab.sort(new Comparator<VocabularyModel>() {
                @Override
                public int compare(VocabularyModel vocabularyModel, VocabularyModel t1) {
                    return t1.getRating().compareTo(vocabularyModel.getRating());
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

    public File exportVocabulary() throws IOException {
        //Get all vocabulary and save in List
        List<VocabularyModel> vocabularies = vocabularyRepo.findAll();
        //Create File
        Files.deleteIfExists(Paths.get("backup.txt"));
        File backup_file = new File("backup.txt");
        FileWriter file_w = new FileWriter(backup_file);

        //Convert Vocabulary Models to GSON (via GSON Library)
        Gson gson = new Gson();
        JsonArray json_arr = new JsonArray();
        String jsonString = null;

        for (VocabularyModel vocabM : vocabularies
        ) {
            jsonString = gson.toJson(vocabM);
            json_arr.add(jsonString);
        }
        file_w.write(json_arr.toString());
        file_w.flush();
        file_w.close();

        return backup_file;
    }

    public Boolean importVocabulary(String content) {

        //GSON Obejct
        Gson gson = new Gson();

        JsonArray JsonArray = JsonParser.parseString(content).getAsJsonArray();

        for (JsonElement json_Ele : JsonArray
        ) {
            //Delete all \ from the JSONArray
            String substring = json_Ele.getAsString().replaceAll("\\\\", "");

            JsonObject jsonObject = new Gson().fromJson(substring, JsonObject.class);
            VocabularyModel vocabM_des = gson.fromJson(jsonObject, VocabularyModel.class);

            Topic topic = vocabM_des.getTopic();
            String vocabulary = vocabM_des.getVocabulary();
            int rating = vocabM_des.getRating();

            //List of TranslationModels to save each of them
            List<TranslationModel> translationModels = new ArrayList<>();
            for (TranslationModel transM : vocabM_des.getTranslationVocabMapping()
            ) {
                Languages lang = transM.getLanguage();
                String vocabulary_trans = transM.getVocabulary();
                Map<Languages, String> translations = new HashMap<>();
                translations.put(lang, vocabulary_trans);
                translations.forEach((k, v) -> {
                    TranslationModel translationModel = new TranslationModel(k, v);
                    translationModels.add(translationModel);
                    translationRepo.save(translationModel);
                });
            }
            VocabularyModel vocabularyModel = new VocabularyModel(topic, vocabulary, translationModels, rating);
            vocabularyRepo.save(vocabularyModel);
        }
        return true;
    }

    public List<VocabularyModel> sortTopics(Topic msg)
    {
        return vocabularyRepo.findByTopic(msg);
    }
}
