package at.tugraz.asd.LANG.Repo;

import at.tugraz.asd.LANG.Model.VocabularyModel;
import at.tugraz.asd.LANG.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VocabularyRepo extends JpaRepository<VocabularyModel, UUID> {

    public VocabularyModel findByVocabulary(String vocab);
    //@Query("Slect...")
    public List<VocabularyModel> findByTopic(Topic topic);
}
