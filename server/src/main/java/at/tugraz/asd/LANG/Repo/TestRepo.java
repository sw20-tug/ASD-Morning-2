package at.tugraz.asd.LANG.Repo;

import at.tugraz.asd.LANG.Model.TestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TestRepo extends JpaRepository<TestModel, UUID> {
    TestModel findByUserName(String userName);
}
