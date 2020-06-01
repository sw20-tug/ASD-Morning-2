package at.tugraz.asd.LANG.ServiceTests;

import at.tugraz.asd.LANG.Model.TestModel;
import at.tugraz.asd.LANG.Repo.TestRepo;
import at.tugraz.asd.LANG.Service.TestingModeService;
import org.hibernate.engine.jdbc.ClobProxy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestingModeServiceTest {

    @Autowired
    TestingModeService service;

    @MockBean
    TestRepo testRepo;

    @Test
    public void saveTestOnEmptyRow() throws Exception {
        TestModel testModel = new TestModel("HansMartin", ClobProxy.generateProxy("a long string"));
        when(testRepo.save(any(TestModel.class))).thenReturn(testModel);

        TestModel returnTestModel = service.saveTest("a long string");
        Assert.assertEquals(returnTestModel.getTestStates(), testModel.getTestStates());
        Assert.assertEquals(returnTestModel.getUserName(), testModel.getUserName());
    }

    @Test
    public void saveTestOverExistingTest() throws Exception {
        TestModel testModel = new TestModel("HansMartin", ClobProxy.generateProxy("a long string"));
        //TestModel oldTestModel = new TestModel("HansMartin", ClobProxy.generateProxy("a longer string"));

        given(testRepo.save(testModel)).willReturn(testModel);
        TestModel oldTestModel = service.saveTest("a longer string");

        verify(testRepo).save(any(TestModel.class));
    }

    @Test
    public void findByUserNameShouldReturnTestModel() throws Exception {
        TestModel testModel = new TestModel("HansMartin", ClobProxy.generateProxy("a long string"));
        String givenString = "a long string";

        when(testRepo.findByUserName("HansMartin")).thenReturn(testModel);
        Assert.assertEquals(givenString, testModel.getTestStates().getSubString(1, (int) testModel.getTestStates().length()));
    }
}
