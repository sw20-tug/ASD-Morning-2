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
    private static final String USER_1 ="User 1";


    @Test
    public void testFindByUsername_exists(){
        TestModel model = createTestModel();
        testRepo.save(model);
        Assert.assertEquals(testRepo.findByUserName(USER_1),model);
    }

    @Test
    public void testFindByUsername_doesNotExist(){
        Assert.assertNull(testRepo.findByUserName(USER_1));
    }

    //helper
    public TestModel createTestModel(){
        return new TestModel(USER_1, ClobProxy.generateProxy("a long string"));
    }
}
