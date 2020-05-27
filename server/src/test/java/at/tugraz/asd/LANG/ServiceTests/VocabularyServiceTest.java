package at.tugraz.asd.LANG.ServiceTests;

import at.tugraz.asd.LANG.Exeptions.EditFail;
import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Messages.in.CreateVocabularyMessageIn;
import at.tugraz.asd.LANG.Messages.in.EditVocabularyMessageIn;
import at.tugraz.asd.LANG.Model.TranslationModel;
import at.tugraz.asd.LANG.Model.VocabularyModel;
import at.tugraz.asd.LANG.Repo.TranslationRepo;
import at.tugraz.asd.LANG.Repo.VocabularyRepo;
import at.tugraz.asd.LANG.Service.VocabularyService;
import at.tugraz.asd.LANG.Topic;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public void createVocabulary(){
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


    //Helper

    private List<VocabularyModel> getAllVocabularyMockData() {
        List<TranslationModel> translations = Stream.of(
                new TranslationModel(Languages.DE,"bread"),
                new TranslationModel(Languages.FR,"pain"),
                new TranslationModel(Languages.DE,"brot")
        ).collect(Collectors.toList());
        VocabularyModel vocabularyModel = new VocabularyModel(Topic.USER_GENERATED, "brot", translations, Integer.valueOf(0));

        List<TranslationModel> translations1 = Stream.of(
                new TranslationModel(Languages.DE,"haus"),
                new TranslationModel(Languages.FR,"maison"),
                new TranslationModel(Languages.DE,"house")
        ).collect(Collectors.toList());
        VocabularyModel vocabularyModel1 = new VocabularyModel(Topic.USER_GENERATED, "haus", translations1, Integer.valueOf(0));
        return Stream.of(vocabularyModel,vocabularyModel1).collect(Collectors.toList());
    }

    private List<TranslationModel> getEditedTranslationModel()
    {
        return Stream.of(
                new TranslationModel(Languages.DE,"bread"),
                new TranslationModel(Languages.FR,"pain"),
                new TranslationModel(Languages.DE,"brot")
        ).collect(Collectors.toList());
    }

    private VocabularyModel getEditedVocabularyModel() {
        //create expected return value
        List<TranslationModel> translations = Stream.of(
                new TranslationModel(Languages.DE,"bread"),
                new TranslationModel(Languages.FR,"pain"),
                new TranslationModel(Languages.DE,"brot")
        ).collect(Collectors.toList());
        VocabularyModel vocabularyModel = new VocabularyModel(Topic.USER_GENERATED, "brot", translations, Integer.valueOf(0));
        return vocabularyModel;
    }

    private   VocabularyModel getBasicVocabularyModel(){
        //create expected return value
        List<TranslationModel> translations = Stream.of(
                new TranslationModel(Languages.DE,"haus"),
                new TranslationModel(Languages.FR,"maison"),
                new TranslationModel(Languages.DE,"house")
        ).collect(Collectors.toList());
        VocabularyModel vocabularyModel = new VocabularyModel(Topic.USER_GENERATED, "haus", translations, Integer.valueOf(0));
        return vocabularyModel;
    }

}
