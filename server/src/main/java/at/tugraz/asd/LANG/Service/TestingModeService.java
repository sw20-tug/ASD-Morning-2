package at.tugraz.asd.LANG.Service;

import at.tugraz.asd.LANG.Messages.in.SaveTestRequest;
import at.tugraz.asd.LANG.Model.TestModel;
import at.tugraz.asd.LANG.Repo.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestingModeService {
    TestRepo testRepo;

    @Autowired
    public TestingModeService(TestRepo testRepo) {
        this.testRepo = testRepo;
    }

    public TestModel saveTest(SaveTestRequest saveRequest) {
        return null;
    }
}
