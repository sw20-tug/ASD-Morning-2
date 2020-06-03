package at.tugraz.asd.LANG.Service;

import at.tugraz.asd.LANG.Model.TestModel;
import at.tugraz.asd.LANG.Repo.TestRepo;
import org.hibernate.engine.jdbc.ClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class TestingModeService {
    TestRepo testRepo;

    @Autowired
    public TestingModeService(TestRepo testRepo) { this.testRepo = testRepo; }

    public TestModel saveTest(String testStates) {
        TestModel oldTest = testRepo.findByUserName("HansMartin");  // Hardcode name because single user
        if (oldTest != null) {
            oldTest.setTestStates(ClobProxy.generateProxy(testStates));
            return testRepo.save(oldTest);
        } else {
            TestModel newTest = new TestModel();
            newTest.setUserName("HansMartin");
            newTest.setTestStates(ClobProxy.generateProxy(testStates));
            return testRepo.save(newTest);
        }
    }

    public String getTest() {
        TestModel testModel = testRepo.findByUserName("HansMartin");
        String testStatesString = "";
        try {
            testStatesString = testModel.getTestStates().getSubString(1, (int) testModel.getTestStates().length());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testStatesString;
    }

    public boolean checkForSavedTest() {
        return testRepo.findByUserName("HansMartin") != null;
    }
}
