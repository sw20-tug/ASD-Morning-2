package at.tugraz.asd.LANG.ControllerTests;


import at.tugraz.asd.LANG.Controller.VocabularyController;
import at.tugraz.asd.LANG.Exeptions.EditFail;
import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Messages.in.CreateVocabularyMessageIn;
import at.tugraz.asd.LANG.Messages.in.EditVocabularyMessageIn;
import at.tugraz.asd.LANG.Messages.in.ShareMessageIn;
import at.tugraz.asd.LANG.Messages.out.VocabularyOut;
import at.tugraz.asd.LANG.Model.TranslationModel;
import at.tugraz.asd.LANG.Model.VocabularyModel;
import at.tugraz.asd.LANG.Service.VocabularyService;
import at.tugraz.asd.LANG.Topic;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.mail.MessagingException;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(VocabularyController.class)
public class VocabularyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VocabularyService service;

    @Autowired
    private VocabularyController controller;



    @Test
    public void testAddVocabulary() throws Exception {

        //create input for mvc call
        HashMap<Languages, String> translation = new HashMap<>();
        translation.put(Languages.DE, "haus");
        translation.put(Languages.FR, "maison");
        translation.put(Languages.EN, "house");
        CreateVocabularyMessageIn msg = new CreateVocabularyMessageIn("haus", Topic.Home ,translation);

        //create expected return value
        List<TranslationModel> translations = new ArrayList<>();
        translations.add(new TranslationModel(Languages.DE,"haus"));
        translations.add(new TranslationModel(Languages.FR,"maison"));
        translations.add(new TranslationModel(Languages.DE,"house"));
        VocabularyModel vocabularyModel = new VocabularyModel(Topic.Home,"haus",translations, 0);


        when(service.saveVocabulary(msg)).thenReturn(
                vocabularyModel
        );

        //perform save-call to endpoint
        mvc.perform(post("/api/vocabulary")
                .content(asJsonString(msg))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void testGetAllVocabularyNoContentFound() throws Exception {
        when(service.getAllVocabulary()).thenReturn(Collections.EMPTY_LIST);
        mvc.perform(get("/api/vocabulary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }



   @Test
   public void testGetAllVocabularyFail() throws Exception {
       List<TranslationModel> translations_1 = new ArrayList<>();
       translations_1.add(new TranslationModel(Languages.DE,"haus"));
       translations_1.add(new TranslationModel(Languages.FR,"maison"));
       translations_1.add(new TranslationModel(Languages.DE,"house"));

       when(service.getAllVocabulary()).thenReturn(
               Stream.of(
                       new VocabularyModel(Topic.USER_GENERATED,"haus",translations_1, 0)
              ).collect(Collectors.toList())
      );

       String getResult = mvc.perform(get("/api/vocabulary/")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk()).andReturn()
               .getResponse().getContentAsString();
       ObjectMapper mapper = new ObjectMapper();

       List<VocabularyOut> getVocab = mapper.readValue(getResult,mapper.getTypeFactory().constructCollectionType(List.class, VocabularyOut.class));

       //Expected ReturnValue
       // [{"topic":"USER_GENERATED","vocabulary":"haus","translations":{"FR":"maison","DE":"house"}}]
       HashMap<Languages, String> expexted_translation = new HashMap<>();
       expexted_translation.put(Languages.FR, "maison");
       expexted_translation.put(Languages.EN, "house");

       VocabularyOut expected_vocabulary = new VocabularyOut(Topic.USER_GENERATED, "haus", expexted_translation, 0);


       //assert values
       Assert.assertEquals(getVocab.size(),1);
       Assert.assertEquals(getVocab.get(0).getTopic(),Topic.USER_GENERATED);
       Assert.assertEquals(getVocab.get(0).getVocabulary(),"haus");
       Assert.assertEquals(getVocab.get(0).getTranslations().get(Languages.FR), expected_vocabulary.getTranslations().get(Languages.FR));

   }


    @Test
    public void testEdit() throws Exception {

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                return null;
            }
        }).when(service).editVocabulary(any());

        mvc.perform(put("/api/vocabulary")
                .content(asJsonString(new EditVocabularyMessageIn()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //assertion is done by andExpect

    }

    @Test
   public void testEditFail() throws Exception {
        doThrow(EditFail.class).when(service).editVocabulary(any());

        mvc.perform(put("/api/vocabulary")
                .content(asJsonString(new EditVocabularyMessageIn()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        //assertion is done by andExpect

   }

    @Test
    public void testGetRandomVocabularyNoContentFound() throws Exception {
        when(service.getAllVocabulary()).thenReturn(Collections.EMPTY_LIST);
        mvc.perform(get("/api/vocabulary/random")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetRandomVocabulary() throws Exception {
        List<TranslationModel> translations = new ArrayList<>();
        List<VocabularyModel> randomVocabList = new ArrayList<>();
        translations.add(new TranslationModel(Languages.DE, "Brot"));
        translations.add(new TranslationModel(Languages.FR, "baguette"));
        translations.add(new TranslationModel(Languages.EN, "bread"));

        VocabularyModel randomVocab = new VocabularyModel(Topic.Food, "Brot", translations, Integer.valueOf(0));
        for (int i = 0; i < 10; i++) {
            randomVocabList.add(randomVocab);
        }

        given(service.getAllVocabulary()).willReturn(randomVocabList);
        mvc.perform(get("/api/vocabulary/random")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[0].vocabulary").value(randomVocab.getVocabulary()));
    }

    @Test
    public void testExportFail() throws Exception {
        mvc.perform(get("/api/vocabulary/Export")
                .contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void ImportBackup() throws Exception {
        String endpoint = "/api/vocabulary/Import";
        Boolean expectFalse = false;
        File file = new File("backup.txt");
        byte[] fileContent = Files.readAllBytes(file.toPath());

        MockMultipartFile multipartFile = new MockMultipartFile("file", fileContent);

        MvcResult result = mvc.perform(fileUpload(endpoint)
                .file(multipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andReturn();

        String testFalse = result.getResponse().getContentAsString();

        Assert.assertEquals(expectFalse.toString(), testFalse);

    }

   //HELPER
   public static String asJsonString(final Object obj) {
       try {
           return new ObjectMapper().writeValueAsString(obj);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }

    @Test
    public void testFilterUpDownStudyInterfaceDE() throws Exception {
        List<TranslationModel> translations_1 = new ArrayList<>();
        translations_1.add(new TranslationModel(Languages.DE,"haus"));
        translations_1.add(new TranslationModel(Languages.FR,"maison"));
        translations_1.add(new TranslationModel(Languages.DE,"house"));

        when(service.sortVocabStudyInterface(Languages.DE, "a")).thenReturn(
                Stream.of(
                        new VocabularyModel(Topic.USER_GENERATED,"haus",translations_1, 0)
                ).collect(Collectors.toList())
        );
        mvc.perform(get("/api/vocabulary/alphabetically/FR/a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mvc.perform(get("/api/vocabulary/alphabetically/EN/a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mvc.perform(get("/api/vocabulary/alphabetically/DE/a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(get("/api/vocabulary/alphabetically/DE/z")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFilterUpDownStudyInterfaceEN() throws Exception {
        List<TranslationModel> translations_1 = new ArrayList<>();
        translations_1.add(new TranslationModel(Languages.DE,"haus"));
        translations_1.add(new TranslationModel(Languages.FR,"maison"));
        translations_1.add(new TranslationModel(Languages.DE,"house"));

        when(service.sortVocabStudyInterface(Languages.EN, "a")).thenReturn(
                Stream.of(
                        new VocabularyModel(Topic.USER_GENERATED,"haus",translations_1, 0)
                ).collect(Collectors.toList())
        );
        mvc.perform(get("/api/vocabulary/alphabetically/DE/a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mvc.perform(get("/api/vocabulary/alphabetically/FR/a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mvc.perform(get("/api/vocabulary/alphabetically/EN/a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(get("/api/vocabulary/alphabetically/EN/z")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFilterUpDownStudyInterfaceFR() throws Exception {
        List<TranslationModel> translations_1 = new ArrayList<>();
        translations_1.add(new TranslationModel(Languages.DE,"haus"));
        translations_1.add(new TranslationModel(Languages.FR,"maison"));
        translations_1.add(new TranslationModel(Languages.DE,"house"));

        when(service.sortVocabStudyInterface(Languages.FR, "a")).thenReturn(
                Stream.of(
                        new VocabularyModel(Topic.USER_GENERATED,"haus",translations_1, 0)
                ).collect(Collectors.toList())
        );
        mvc.perform(get("/api/vocabulary/alphabetically/DE/a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mvc.perform(get("/api/vocabulary/alphabetically/FR/a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(get("/api/vocabulary/alphabetically/EN/a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mvc.perform(get("/api/vocabulary/alphabetically/FR/z")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFilterUpDownOverview() throws Exception {
        List<TranslationModel> translations_1 = new ArrayList<>();
        translations_1.add(new TranslationModel(Languages.DE,"haus"));
        translations_1.add(new TranslationModel(Languages.FR,"maison"));
        translations_1.add(new TranslationModel(Languages.EN,"house"));

        testFilterHelper1(translations_1);
        testFilterHelper2(translations_1);
    }
    private void testFilterHelper1(List<TranslationModel> translations) throws Exception {
        when(service.sortVocabOverview("a")).thenReturn(
                Stream.of(
                        new VocabularyModel(Topic.USER_GENERATED,"haus",translations, 0)
                ).collect(Collectors.toList())
        );
        mvc.perform(get("/api/vocabulary/alphabetically/z")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mvc.perform(get("/api/vocabulary/alphabetically/a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    private void testFilterHelper2(List<TranslationModel> translations) throws Exception {
        when(service.sortVocabOverview("z")).thenReturn(
                Stream.of(
                        new VocabularyModel(Topic.USER_GENERATED,"haus",translations, 0)
                ).collect(Collectors.toList())
        );
        when(service.sortVocabOverview("a")).thenReturn(Collections.EMPTY_LIST);
        mvc.perform(get("/api/vocabulary/alphabetically/z")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(get("/api/vocabulary/alphabetically/a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFilterUpDownOverviewRating() throws Exception {
        List<TranslationModel> translations_1 = new ArrayList<>();
        translations_1.add(new TranslationModel(Languages.DE,"haus"));
        translations_1.add(new TranslationModel(Languages.FR,"maison"));
        translations_1.add(new TranslationModel(Languages.EN,"house"));

        testFilterHelperRating1(translations_1);
        testFilterHelperRating2(translations_1);
    }
    private void testFilterHelperRating1(List<TranslationModel> translations) throws Exception {
        when(service.sortRating("c")).thenReturn(
                Stream.of(
                        new VocabularyModel(Topic.USER_GENERATED,"haus",translations, 1)
                ).collect(Collectors.toList())
        );
        mvc.perform(get("/api/vocabulary/rating/d")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mvc.perform(get("/api/vocabulary/rating/c")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    private void testFilterHelperRating2(List<TranslationModel> translations) throws Exception {
        when(service.sortRating("d")).thenReturn(
                Stream.of(
                        new VocabularyModel(Topic.USER_GENERATED,"haus",translations, 2)
                ).collect(Collectors.toList())
        );
        when(service.sortRating("c")).thenReturn(Collections.EMPTY_LIST);
        mvc.perform(get("/api/vocabulary/rating/d")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(get("/api/vocabulary/rating/c")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void FilterTopics() throws Exception
    {
        when(service.sortTopics(Topic.Home)).thenReturn(getAllVocabTopicHome());

       MvcResult result =  mvc.perform(get("/api/vocabulary/sort_topics/Home")
                .content(asJsonString(""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        Integer rating = new Integer(0);

        List<VocabularyOut> sortedTopic_retval = mapper.readValue(result.getResponse().getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, VocabularyOut.class));
        Assert.assertEquals(sortedTopic_retval.get(0).getTopic(), Topic.Home);
        Assert.assertEquals(sortedTopic_retval.get(0).getVocabulary(), "haus");
        Assert.assertEquals(sortedTopic_retval.get(0).getRating(), rating);
        Map<Languages, String> expected_translations = new HashMap<Languages, String>();
        expected_translations.put(Languages.DE,"haus");
        expected_translations.put(Languages.FR,"maison");
        expected_translations.put(Languages.EN,"house");
        Assert.assertEquals(sortedTopic_retval.get(0).getTranslations(),expected_translations);
    }



    private List<VocabularyModel> getAllVocabTopicHome() {

        List<TranslationModel> translations1 = Stream.of(
                new TranslationModel(Languages.DE,"haus"),
                new TranslationModel(Languages.FR,"maison"),
                new TranslationModel(Languages.EN,"house")
        ).collect(Collectors.toList());
        VocabularyModel vocabularyModel1 = new VocabularyModel(Topic.Home, "haus", translations1, Integer.valueOf(0));
        return Stream.of(vocabularyModel1).collect(Collectors.toList());
    }
}