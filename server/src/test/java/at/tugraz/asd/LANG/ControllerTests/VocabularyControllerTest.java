package at.tugraz.asd.LANG.ControllerTests;


import at.tugraz.asd.LANG.Controller.VocabularyController;
import at.tugraz.asd.LANG.Exeptions.EditFail;
import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Messages.in.CreateVocabularyMessageIn;
import at.tugraz.asd.LANG.Messages.in.EditVocabularyMessageIn;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private VocabularyService service;



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

        //TODO @Lorenz assertions
    }


    @Test
    public void testGetAllVocabularyNoContentFound() throws Exception {
        when(service.getAllVocabulary()).thenReturn(Collections.EMPTY_LIST);
        mvc.perform(get("/api/vocabulary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Ignore
    @Test
    public void testAddManyVocabulary() throws Exception {

        //First
        HashMap<Languages, String> translation_1 = new HashMap<>();
        translation_1.put(Languages.DE, "haus");
        translation_1.put(Languages.FR, "maison");
        translation_1.put(Languages.EN, "house");
        CreateVocabularyMessageIn msg_1 = new CreateVocabularyMessageIn("haus", Topic.Home,translation_1);

        //create expected return value
        List<TranslationModel> translations_1 = new ArrayList<>();
        translations_1.add(new TranslationModel(Languages.DE,"haus"));
        translations_1.add(new TranslationModel(Languages.FR,"maison"));
        translations_1.add(new TranslationModel(Languages.DE,"house"));
        VocabularyModel vocabularyModel_1 = new VocabularyModel(Topic.Home,"haus",translations_1, 0);


        //perform save-call to endpoint
        mvc.perform(post("/api/vocabulary")
                .content(asJsonString(msg_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<VocabularyModel> ret_1 = new ArrayList<>();
        ret_1.add(vocabularyModel_1);
        given(service.getAllVocabulary()).willReturn(ret_1);


        //Second
        HashMap<Languages, String> translation_2 = new HashMap<>();
        translation_2.put(Languages.DE, "kind");
        translation_2.put(Languages.FR, "lekind");
        translation_2.put(Languages.EN, "child");
        CreateVocabularyMessageIn msg_2 = new CreateVocabularyMessageIn("kind", Topic.Human ,translation_2);

        //create expected return value
        List<TranslationModel> translations_2 = new ArrayList<>();
        translations_2.add(new TranslationModel(Languages.DE,"kind"));
        translations_2.add(new TranslationModel(Languages.FR,"lekind"));
        translations_2.add(new TranslationModel(Languages.DE,"child"));
        VocabularyModel vocabularyModel_2 = new VocabularyModel(Topic.Human,"kind",translations_2, 0);


        //perform save-call to endpoint
        mvc.perform(post("/api/vocabulary")
                .content(asJsonString(msg_2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<VocabularyModel> ret_2 = new ArrayList<>();
        ret_2.add(vocabularyModel_2);
        given(service.getAllVocabulary()).willReturn(ret_2);

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
        mvc.perform(get("/api/random")
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
        mvc.perform(get("/api/random")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect((ResultMatcher) jsonPath("$[0].vocabulary", is(randomVocab.getVocabulary())));
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
    public void exportBackup() throws Exception {

        File backup = service.exportVocabulary();
        Path path = Paths.get(backup.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        mvc.perform(put("/api/vocabulary/Export")
                .content(asJsonString(resource))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void ImportBackup() throws Exception {
        MockMultipartFile csvFile = new MockMultipartFile("file", "backup.csv", MediaType.TEXT_PLAIN_VALUE, "backup.csv".getBytes());

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/import")
                .file(csvFile)
                .param("file"))
                .andExpect(status().is(200))
                .andExpect(content().string("success"));
    }

}