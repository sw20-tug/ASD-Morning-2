package at.tugraz.asd.LANG.Repo;

import at.tugraz.asd.LANG.Model.VocabularyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VocabularyRepo extends JpaRepository<VocabularyModel, UUID> {

    public VocabularyModel findByVocabulary(String vocab);
}
