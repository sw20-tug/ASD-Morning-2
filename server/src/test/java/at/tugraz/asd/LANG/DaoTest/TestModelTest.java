package at.tugraz.asd.LANG.DaoTest;

import at.tugraz.asd.LANG.Model.TestModel;
import at.tugraz.asd.LANG.Repo.TestRepo;
import org.hibernate.engine.jdbc.ClobProxy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestModelTest {

    @Autowired
    TestRepo testRepo;

    @Test
    public void testSaveTest() throws Exception {
        TestModel testModel = new TestModel("HansMartin", ClobProxy.generateProxy("a long string"));

        TestModel savedModel = testRepo.save(testModel);
        Assert.assertEquals(testModel.getUserName(), savedModel.getUserName());
        Assert.assertEquals("a long string", savedModel.getTestStates().getSubString(1, (int) testModel.getTestStates().length()));
    }
}
