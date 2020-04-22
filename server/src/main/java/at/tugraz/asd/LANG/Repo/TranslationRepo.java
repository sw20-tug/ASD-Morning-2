package at.tugraz.asd.LANG.Repo;

import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Model.TranslationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TranslationRepo extends JpaRepository<TranslationModel, UUID> {
    public TranslationModel  findByLanguage(Languages language);
}
