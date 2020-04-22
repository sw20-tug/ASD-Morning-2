package at.tugraz.asd.LANG.ServiceTests;


import at.tugraz.asd.LANG.Controller.VocabularyController;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



import java.lang.reflect.Array;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VocabularyController.class)
public class VocabularyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VocabularyService service;

    @Test
    public void testAddVocabulary() throws Exception {

        //create input for mvc call
        HashMap<Languages, String> translation = new HashMap<>();
        translation.put(Languages.DE, "haus");
        translation.put(Languages.FR, "maison");
        translation.put(Languages.EN, "house");
        CreateVocabularyMessageIn msg = new CreateVocabularyMessageIn("haus", translation);

        //create expected return value
        List<TranslationModel> translations = new ArrayList<>();
        translations.add(new TranslationModel(Languages.DE,"haus"));
        translations.add(new TranslationModel(Languages.FR,"maison"));
        translations.add(new TranslationModel(Languages.DE,"house"));
        VocabularyModel vocabularyModel = new VocabularyModel(Topic.USER_GENERATED,"haus",translations);

        //define return value for service
        given(service.saveVocabulary(msg)).willReturn(vocabularyModel);

        //perform save-call to endpoint
        mvc.perform(post("/api/vocabulary")
                .content(asJsonString(msg))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        //retrive added vocabulary
        //define expected service action on get
        List<VocabularyModel> ret = new ArrayList<>();
        ret.add(vocabularyModel);
        given(service.getAllVocabulary()).willReturn(ret);

        //perform get-call to endpoint

        String getResult = mvc.perform(get("/api/vocabulary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<VocabularyOut> getVocab = mapper.readValue(getResult,mapper.getTypeFactory().constructCollectionType(List.class, VocabularyOut.class));

        //assert values
        Assert.assertEquals(getVocab.size(),1);
        Assert.assertEquals(getVocab.get(0).getTopic(),Topic.USER_GENERATED);
        Assert.assertEquals(getVocab.get(0).getVocabulary(),"haus");
        Assert.assertEquals(getVocab.get(0).getTranslations(),translation);



    }

    @Test
    public void testGetAllVocabularyNoContentFound() throws Exception {
        mvc.perform(get("/api/vocabulary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    //TODO
   @Test
   public void testGetAllVocabulary(){}

   //TODO
   @Test
   public void testGetInvalidVocabulary()
   {

   }

    @Test
    public void testEditValidVocabulary() throws Exception
    {
        //Create Data To Manipulate
       // testAddVocabulary();
        /* Expected data
         {
                "current_translations":
                {
                "DE" : "haus",
                "EN" : "maison",
                "FR" : "house"
                },

                "new_translations":
                {
                "DE" : "villa",
                "EN" : "villa",
                "FR" : "mansion"
                }
        }
        */

        HashMap<Languages, String> current_translations = new HashMap<>();
        current_translations.put(Languages.DE, "haus");
        current_translations.put(Languages.FR, "maison");
        current_translations.put(Languages.EN, "house");

        HashMap<Languages, String> new_translations = new HashMap<>();
        new_translations.put(Languages.DE, "villa");
        new_translations.put(Languages.FR, "villa");
        new_translations.put(Languages.EN, "mansion");

        EditVocabularyMessageIn msg = new EditVocabularyMessageIn(current_translations, new_translations);

        given(service.editVocabulary(msg)).willReturn(200);

        mvc.perform(put("/api/vocabulary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

   //HELPER
   public static String asJsonString(final Object obj) {
       try {
           return new ObjectMapper().writeValueAsString(obj);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }


}
