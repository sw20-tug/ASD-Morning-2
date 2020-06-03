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

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.OptionalDouble.empty;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VocabularyModelTest {

    @Autowired
    VocabularyRepo vocabularyRepo;
    @Autowired
    TranslationRepo translationRepo;

    @Test
    public void testFindByVocabulary_vocabularyExists(){
        VocabularyModel model = generateBasicModel();
        List<TranslationModel> translationModels = Stream.of(
                new TranslationModel(Languages.DE,"haus"),
                new TranslationModel(Languages.FR,"maison"),
                new TranslationModel(Languages.EN,"house")
        ).collect(Collectors.toList());
        translationRepo.saveAll(translationModels);
        vocabularyRepo.saveAndFlush(new VocabularyModel(Topic.Home,"haus",translationModels,0));
        VocabularyModel returnValue = vocabularyRepo.findByVocabulary("haus");

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

    @Test
    public void testFindByVocabulary_vocabularyDoesNotExist(){
        Assert.assertNull(vocabularyRepo.findByVocabulary("maus"));
    }

    @Test
    public void testFindByTopic_topicExists(){
        VocabularyModel model = generateBasicModel();
        List<TranslationModel> translationModels = Stream.of(
                new TranslationModel(Languages.DE,"haus"),
                new TranslationModel(Languages.FR,"maison"),
                new TranslationModel(Languages.EN,"house")
        ).collect(Collectors.toList());
        translationRepo.saveAll(translationModels);
        vocabularyRepo.saveAndFlush(new VocabularyModel(Topic.Home,"haus",translationModels,0));
        VocabularyModel returnValue = vocabularyRepo.findByVocabulary("haus");

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

    @Test
    public void testFindByTopic_topicDoesNotExist(){
        Assert.assertEquals(vocabularyRepo.findByTopic(Topic.USER_GENERATED), Collections.EMPTY_LIST);
    }


    //helper
    public VocabularyModel generateBasicModel(){
        HashMap<Languages, String> translation = new HashMap<>();
        translation.put(Languages.DE, "haus");
        translation.put(Languages.FR, "maison");
        translation.put(Languages.EN, "house");
        CreateVocabularyMessageIn msg = new CreateVocabularyMessageIn("haus", Topic.Home ,translation);

        //create expected return value
        List<TranslationModel> translations = new ArrayList<>();
        translations.add(new TranslationModel(Languages.DE,"haus"));
        translations.add(new TranslationModel(Languages.FR,"maison"));
        translations.add(new TranslationModel(Languages.EN,"house"));
        VocabularyModel vocabularyModel = new VocabularyModel(Topic.Home,"haus",translations, 0);

        return vocabularyModel;

    }


}