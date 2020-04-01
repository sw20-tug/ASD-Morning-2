package at.tugraz.asd.LANG.DaoTest;

import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Messages.in.CreateVocabularyMessageIn;
import at.tugraz.asd.LANG.Model.TranslationModel;
import at.tugraz.asd.LANG.Model.VocabularyModel;
import at.tugraz.asd.LANG.Repo.TranslationRepo;
import at.tugraz.asd.LANG.Repo.VocabularyRepo;
import at.tugraz.asd.LANG.Topic;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VocabularyModelTest {

    @Autowired
    TranslationRepo translationRepo;

    @Autowired
    VocabularyRepo vocabularyRepo;

    @Test
    public void testSaveVocabulary(){

        HashMap<Languages, String> translation = new HashMap<>();
        translation.put(Languages.DE, "haus");
        translation.put(Languages.FR, "maison");
        translation.put(Languages.EN, "house");

        CreateVocabularyMessageIn msg = new CreateVocabularyMessageIn("haus", translation);
        Map<Languages, String> translations = msg.getTranslations();
        String vocab = msg.getVocabulary();

        List<TranslationModel> translationModels = new ArrayList<>();
        translations.forEach((k,v)->{
            TranslationModel translationModel = new TranslationModel(k, v);
            translationModels.add(translationModel);
            translationRepo.save(translationModel);
        });

        VocabularyModel vocabularyModel = new VocabularyModel(Topic.USER_GENERATED, vocab, translationModels);
        VocabularyModel res = vocabularyRepo.save(vocabularyModel);
        Assert.assertEquals(3, res.getTranslationVocabMapping().size());
        Assert.assertArrayEquals(translationModels.toArray(), res.getTranslationVocabMapping().toArray());

        //test getAll vocabulary
        List<VocabularyModel> new_list = vocabularyRepo.findAll();
        Assert.assertEquals(1, new_list.size());
    }
}
