package at.tugraz.asd.LANG.ControllerTests;

import at.tugraz.asd.LANG.Controller.TestingModeController;
import at.tugraz.asd.LANG.Model.TestModel;
import at.tugraz.asd.LANG.Service.TestingModeService;
import org.hibernate.engine.jdbc.ClobProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TestingModeController.class)
public class TestingModeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TestingModeService service;

    @Test
    public void saveTestShouldSaveATestModel() throws Exception {
        TestModel testModel = new TestModel("HansMartin", ClobProxy.generateProxy("a long string"));
        when(service.saveTest("a long string")).thenReturn(testModel);
        mvc.perform(post("/api/testing_mode/save")
                .content("a long string"))
                .andExpect(status().isOk());
    }

    @Test
    public void getTestShouldReturnAString() throws Exception {
        when(service.getTest()).thenReturn("a long string");
        mvc.perform(get("/api/testing_mode/continue"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value("a long string"));
    }

    @Test
    public void checkTestShouldReturnTrue() throws Exception {
        when(service.checkForSavedTest()).thenReturn(true);
        mvc.perform(get("/api/testing_mode/check"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void checkTestShouldReturnFalse() throws Exception {
        when(service.checkForSavedTest()).thenReturn(false);
        mvc.perform(get("/api/testing_mode/check"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }
}
