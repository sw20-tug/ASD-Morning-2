package at.tugraz.asd.LANG.ServiceTests;

import at.tugraz.asd.LANG.Exeptions.CreateVocabularyFail;
import at.tugraz.asd.LANG.Exeptions.EditFail;
import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Messages.in.CreateVocabularyMessageIn;
import at.tugraz.asd.LANG.Messages.in.EditVocabularyMessageIn;
import at.tugraz.asd.LANG.Messages.out.VocabularyOut;
import at.tugraz.asd.LANG.Model.TranslationModel;
import at.tugraz.asd.LANG.Model.VocabularyModel;
import at.tugraz.asd.LANG.Repo.TranslationRepo;
import at.tugraz.asd.LANG.Repo.VocabularyRepo;
import at.tugraz.asd.LANG.Service.VocabularyService;
import at.tugraz.asd.LANG.Topic;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VocabularyServiceTest {

    @Autowired
    VocabularyService service;

    @MockBean
    TranslationRepo translationRepo;

    @MockBean
    VocabularyRepo vocabularyRepo;
    private EditVocabularyMessageIn msg;


    @Test
    public void createVocabulary() throws CreateVocabularyFail {
        VocabularyModel model = getBasicVocabularyModel();
        when(vocabularyRepo.save(any(VocabularyModel.class))).thenReturn(model);

        VocabularyModel returnValue = service.saveVocabulary(createVocabularyMessageIn());
        Assert.assertEquals(returnValue.getTopic(),model.getTopic());
        Assert.assertEquals(returnValue.getVocabulary(),model.getVocabulary());

        returnValue.getTranslationVocabMapping().forEach(el -> {
            if(el.getLanguage().equals(Languages.EN))
            {
                model.getTranslationVocabMapping().forEach(e -> {
                    if(e.getLanguage().equals(Languages.EN))
                    {
                        Assert.assertEquals(el.getVocabulary(), e.getVocabulary());
                    }
                });
            }
            if(el.getLanguage().equals(Languages.DE))
            {
                model.getTranslationVocabMapping().forEach(e -> {
                    if(e.getLanguage().equals(Languages.DE))
                    {
                        Assert.assertEquals(el.getVocabulary(), e.getVocabulary());
                    }
                });
            }
            if(el.getLanguage().equals(Languages.FR))
            {
                model.getTranslationVocabMapping().forEach(e -> {
                    if(e.getLanguage().equals(Languages.FR))
                    {
                        Assert.assertEquals(el.getVocabulary(), e.getVocabulary());
                    }
                });
            }
        });
    }

    @Test(expected = CreateVocabularyFail.class)
    public void createVocabularyFail() throws CreateVocabularyFail {
        when(vocabularyRepo.save(any(VocabularyModel.class))).thenReturn(getBasicVocabularyModel());
        Assert.assertEquals(service.saveVocabulary(new CreateVocabularyMessageIn()),getBasicVocabularyModel());
    }

    @Test
    public void editVocabularyChangeTransaltionModelAndVocabularyModel() throws EditFail {
        List<TranslationModel> translationModels = getEditedTranslationModel();
        when(vocabularyRepo.findByVocabulary("haus")).thenReturn(getBasicVocabularyModel());
        when(vocabularyRepo.save(any(VocabularyModel.class))).thenReturn(getEditedVocabularyModel());
        when(translationRepo.save(translationModels.get(0))).thenReturn(translationModels.get(0));
        when(translationRepo.save(translationModels.get(1))).thenReturn(translationModels.get(1));
        when(translationRepo.save(translationModels.get(2))).thenReturn(translationModels.get(2));


        Map<Languages,String> current_translations = new HashMap<>();
        Map<Languages,String> new_translations = new HashMap<>();
        current_translations.put(Languages.DE,"haus");
        current_translations.put(Languages.FR,"mansion");
        current_translations.put(Languages.EN,"house");

        new_translations.put(Languages.DE,"bread");
        new_translations.put(Languages.FR,"pain");
        new_translations.put(Languages.EN,"brot");

        VocabularyModel editedModel = service.editVocabulary(new EditVocabularyMessageIn(
                current_translations, new_translations, 2
        ));
        Assert.assertEquals(editedModel,getEditedVocabularyModel());

    }

    @Test
    public void getAllVocabulary(){
        when(vocabularyRepo.findAll()).thenReturn(getAllVocabularyMockData());
        Assert.assertEquals(getAllVocabularyMockData(),service.getAllVocabulary());
    }

    @Test(expected = EditFail.class)
    public void editTransationFails() throws EditFail {
       when(vocabularyRepo.findByVocabulary(any())).thenReturn(null);
        Map<Languages,String> current_translations = new HashMap<>();
        Map<Languages,String> new_translations = new HashMap<>();
       EditVocabularyMessageIn msg = new EditVocabularyMessageIn(
        current_translations,new_translations,2
       );
       service.editVocabulary(msg);
    }

    @Test
    public void testSortOverviewRatingD() {
        List<VocabularyModel> data = getAllVocabularyMockData();
        VocabularyModel tmp = data.get(0);
        data.set(0, data.get(1));
        data.set(1, tmp);
        when(vocabularyRepo.findAll()).thenReturn(data);
        List<VocabularyModel> newList = service.sortRating("d");
        Assert.assertEquals(getAllVocabularyMockData(),newList);
    }

    @Test
    public void testSortOverviewRatingC() {
        List<VocabularyModel> data = getAllVocabularyMockData();
        VocabularyModel tmp = data.get(0);
        data.set(0, data.get(1));
        data.set(1, tmp);
        when(vocabularyRepo.findAll()).thenReturn(getAllVocabularyMockData());
        List<VocabularyModel> newList = service.sortRating("c");
        Assert.assertEquals(data,newList);
    }

    @Test
    public void testSortOverviewDFailRating() {
        when(vocabularyRepo.findAll()).thenReturn(Collections.emptyList());
        List<VocabularyModel> newList = service.sortRating("d");
        Assert.assertEquals(newList,Collections.EMPTY_LIST);
    }

    @Test
    public void testSortOverviewCFailRating() {
        when(vocabularyRepo.findAll()).thenReturn(Collections.emptyList());
        List<VocabularyModel> newList = service.sortRating("c");
        Assert.assertEquals(newList,Collections.EMPTY_LIST);
    }

    @Test
    public void testSortOverviewA() {
        List<VocabularyModel> data = getAllVocabularyMockData();
        VocabularyModel tmp = data.get(0);
        data.set(0, data.get(1));
        data.set(1, tmp);
        when(vocabularyRepo.findAll()).thenReturn(data);
        List<VocabularyModel> newList = service.sortVocabOverview("a");
        Assert.assertEquals(getAllVocabularyMockData(),newList);
    }

    @Test
    public void testSortOverviewZ() {
        List<VocabularyModel> data = getAllVocabularyMockData();
        VocabularyModel tmp = data.get(0);
        data.set(0, data.get(1));
        data.set(1, tmp);
        when(vocabularyRepo.findAll()).thenReturn(getAllVocabularyMockData());
        List<VocabularyModel> newList = service.sortVocabOverview("z");
        Assert.assertEquals(data,newList);
    }

    @Test
    public void testSortOverviewAFail() {
        when(vocabularyRepo.findAll()).thenReturn(Collections.emptyList());
        List<VocabularyModel> newList = service.sortVocabOverview("a");
        Assert.assertEquals(newList,Collections.EMPTY_LIST);
    }

    @Test
    public void testSortOverviewZFail() {
        when(vocabularyRepo.findAll()).thenReturn(Collections.emptyList());
        List<VocabularyModel> newList = service.sortVocabOverview("z");
        Assert.assertEquals(newList,Collections.EMPTY_LIST);
    }

    @Test
    public void testSortStudyInterfaceADE() {
        List<VocabularyModel> data = getAllVocabularyMockData();
        VocabularyModel tmp = data.get(0);
        data.set(0, data.get(1));
        data.set(1, tmp);
        when(vocabularyRepo.findAll()).thenReturn(data);
        List<VocabularyModel> newList = service.sortVocabStudyInterface(Languages.DE,"a");
        Assert.assertEquals(getAllVocabularyMockData(),newList);
    }

    @Test
    public void testSortStudyInterfaceAEN() {
        List<VocabularyModel> data = getAllVocabularyMockData();
        VocabularyModel tmp = data.get(0);
        data.set(0, data.get(1));
        data.set(1, tmp);
        when(vocabularyRepo.findAll()).thenReturn(data);
        List<VocabularyModel> newList = service.sortVocabStudyInterface(Languages.EN,"a");
        Assert.assertEquals(getAllVocabularyMockData(),newList);
    }

    @Test
    public void testSortStudyInterfaceAFR() {
        List<VocabularyModel> data = getAllVocabularyMockData();
        VocabularyModel tmp = data.get(0);
        data.set(0, data.get(1));
        data.set(1, tmp);
        when(vocabularyRepo.findAll()).thenReturn(getAllVocabularyMockData());
        List<VocabularyModel> newList = service.sortVocabStudyInterface(Languages.FR,"a");
        Assert.assertEquals(data,newList);
    }

    @Test
    public void testSortStudyInterfaceZDE() {
        List<VocabularyModel> data = getAllVocabularyMockData();
        VocabularyModel tmp = data.get(0);
        data.set(0, data.get(1));
        data.set(1, tmp);
        when(vocabularyRepo.findAll()).thenReturn(getAllVocabularyMockData());
        List<VocabularyModel> newList = service.sortVocabStudyInterface(Languages.DE,"z");
        Assert.assertEquals(data,newList);
    }

    @Test
    public void testSortStudyInterfaceZEN() {
        List<VocabularyModel> data = getAllVocabularyMockData();
        VocabularyModel tmp = data.get(0);
        data.set(0, data.get(1));
        data.set(1, tmp);
        when(vocabularyRepo.findAll()).thenReturn(getAllVocabularyMockData());
        List<VocabularyModel> newList = service.sortVocabStudyInterface(Languages.EN,"z");
        Assert.assertEquals(data,newList);
    }

    @Test
    public void testSortStudyInterfaceZFR() {
        List<VocabularyModel> data = getAllVocabularyMockData();
        VocabularyModel tmp = data.get(0);
        data.set(0, data.get(1));
        data.set(1, tmp);
        when(vocabularyRepo.findAll()).thenReturn(data);
        List<VocabularyModel> newList = service.sortVocabStudyInterface(Languages.FR,"z");
        Assert.assertEquals(getAllVocabularyMockData(),newList);
    }

    @Test
    public void testSortStudyInterfaceFail() {
        when(vocabularyRepo.findAll()).thenReturn(Collections.emptyList());
        List<VocabularyModel> newList = service.sortVocabStudyInterface(Languages.DE,"z");
        Assert.assertEquals(newList,Collections.EMPTY_LIST);
    }

    @Test
    public void sortTopics()
    {
        when(vocabularyRepo.findByTopic(Topic.Home)).thenReturn(getAllVocabularyMockData());

        List<VocabularyModel> retval = service.sortTopics(Topic.Home);

        List<VocabularyOut> ret = getSpecificTopic(Topic.Home, retval);

        Integer rating = new Integer(0);
        for(int i = 0; i < ret.size(); i++)
        {
            Assert.assertEquals(ret.get(i).getTopic(), Topic.Home);
        }
    }

    @Test
    public void testExport() throws IOException {
        when(vocabularyRepo.findAll()).thenReturn(getAllVocabularyMockData());
        Files.deleteIfExists(Paths.get("ExportFile.txt"));
        File BackupFile = new File("backup.txt");
        BackupFile = service.exportVocabulary();
        File ExportFile = new File("ExportFile.txt");
        FileWriter ExportFileWriter = new FileWriter(ExportFile);

        Gson gson = new Gson();
        JsonArray json_arr = new JsonArray();
        String jsonString = null;

        for (VocabularyModel vocabM : getAllVocabularyMockData()
        ) {
            jsonString = gson.toJson(vocabM);
            json_arr.add(jsonString);
        }

        ExportFileWriter.write(json_arr.toString());
        ExportFileWriter.flush();
        ExportFileWriter.close();

        String contentBackupFile = Files.readString(BackupFile.toPath(), StandardCharsets.UTF_8);
        String contentExportFile = Files.readString(ExportFile.toPath(), StandardCharsets.UTF_8);

        Assert.assertEquals(contentBackupFile, contentExportFile);
    }

    @Test
    public void testImport() throws Exception {
        when(vocabularyRepo.saveAll(any())).thenReturn(getAllVocabularyMockData());

        File BackupFile = new File("backup.txt");
        String contentBackupFile = Files.readString(BackupFile.toPath(), StandardCharsets.UTF_8);

        Boolean expectSuccess = true;
        Boolean testSuccess = service.importVocabulary(contentBackupFile);

        Assert.assertEquals(expectSuccess, testSuccess);
    }

    //Helper

    private List<VocabularyOut> getSpecificTopic(Topic topic, List<VocabularyModel> model)
    {
        ArrayList<VocabularyOut> ret = new ArrayList<>();

        model.forEach(el->{
            HashMap<Languages, String> translation = new HashMap<>();
            el.getTranslationVocabMapping().forEach(translationModel -> {
                translation.put(translationModel.getLanguage(), translationModel.getVocabulary());
            });
            if(el.getTopic() == topic)
            {
                ret.add(new VocabularyOut(
                        el.getTopic(),
                        el.getVocabulary(),
                        translation,
                        el.getRating()
                ));
            }
        });

        return ret;
    }

    //Beware of changing the return value -> tests for sorting will fail
    private List<VocabularyModel> getAllVocabularyMockData() {
        List<TranslationModel> translations = Stream.of(
                new TranslationModel(Languages.EN,"bread"),
                new TranslationModel(Languages.FR,"pain"),
                new TranslationModel(Languages.DE,"brot")
        ).collect(Collectors.toList());
        VocabularyModel vocabularyModel = new VocabularyModel(Topic.USER_GENERATED, "brot", translations, Integer.valueOf(2));

        List<TranslationModel> translations1 = Stream.of(
                new TranslationModel(Languages.DE,"haus"),
                new TranslationModel(Languages.FR,"maison"),
                new TranslationModel(Languages.EN,"house")
        ).collect(Collectors.toList());
        VocabularyModel vocabularyModel1 = new VocabularyModel(Topic.Home, "haus", translations1, Integer.valueOf(0));
        return Stream.of(vocabularyModel,vocabularyModel1).collect(Collectors.toList());
    }

    private List<TranslationModel> getEditedTranslationModel()
    {
        return Stream.of(
                new TranslationModel(Languages.EN,"bread"),
                new TranslationModel(Languages.FR,"pain"),
                new TranslationModel(Languages.DE,"brot")
        ).collect(Collectors.toList());
    }

    private VocabularyModel getEditedVocabularyModel() {
        //create expected return value
        List<TranslationModel> translations = Stream.of(
                new TranslationModel(Languages.EN,"bread"),
                new TranslationModel(Languages.FR,"pain"),
                new TranslationModel(Languages.DE,"brot")
        ).collect(Collectors.toList());
        VocabularyModel vocabularyModel = new VocabularyModel(Topic.USER_GENERATED, "brot", translations, Integer.valueOf(0));
        return vocabularyModel;
    }

    private VocabularyModel getBasicVocabularyModel(){
        //create expected return value
        List<TranslationModel> translations = Stream.of(
                new TranslationModel(Languages.DE,"haus"),
                new TranslationModel(Languages.FR,"maison"),
                new TranslationModel(Languages.EN,"house")
        ).collect(Collectors.toList());
        VocabularyModel vocabularyModel = new VocabularyModel(Topic.USER_GENERATED, "haus", translations, Integer.valueOf(0));
        return vocabularyModel;
    }

    private CreateVocabularyMessageIn createVocabularyMessageIn(){
        CreateVocabularyMessageIn ret = new CreateVocabularyMessageIn();
        HashMap<Languages,String> translations = new HashMap<>();
        translations.put(Languages.EN,"house");
        translations.put(Languages.DE,"haus");
        translations.put(Languages.FR,"maison");


        ret.setTopic(Topic.USER_GENERATED);
        ret.setTranslations(translations);
        ret.setVocabulary("haus");
        return ret;
    }

}
