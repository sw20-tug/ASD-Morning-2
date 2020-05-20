package at.tugraz.asd.LANG.ServiceTests;

import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Model.TranslationModel;
import at.tugraz.asd.LANG.Model.VocabularyModel;
import at.tugraz.asd.LANG.Repo.TranslationRepo;
import at.tugraz.asd.LANG.Repo.VocabularyRepo;
import at.tugraz.asd.LANG.Service.VocabularyService;
import at.tugraz.asd.LANG.Topic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class VocabularyServiceTest {

    @Autowired
    VocabularyService service;

    @MockBean
    TranslationRepo translationRepo;

    @MockBean
    VocabularyRepo vocabularyRepo;

    @Before
    public void init(){

    }

    @Test
    public void testRatingVocabulary(){

        //return the vocab to edit
        when(vocabularyRepo.findByVocabulary(Mockito.anyString())).thenReturn(
            new VocabularyModel(
                    Topic.USER_GENERATED,
                    "Haus",
                    Stream.of(
                            new TranslationModel(Languages.FR,"Mansion"),
                            new TranslationModel(Languages.EN,"Home")
                    ).collect(Collectors.toList()),
                    Integer.valueOf(0)
            )
        );

        //mock save transaltion
        TranslationModel editedModel =  new TranslationModel(Languages.DE,"Gebäude");
        when(translationRepo.save(
                Mockito.any()
        )).thenReturn(editedModel);

        //mock save of vocab


        //assert rating value

    }

}
