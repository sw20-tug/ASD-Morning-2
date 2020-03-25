package at.tugraz.asd.LANG.Repo;

import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Model.LanguageModel;
import at.tugraz.asd.LANG.Model.VocabularyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LanguagesRepository extends JpaRepository<LanguageModel, Languages> {
}
