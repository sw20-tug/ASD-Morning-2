package at.tugraz.asd.LANG.DaoTest;


import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Model.TranslationModel;
import at.tugraz.asd.LANG.Repo.TranslationRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransalationModelTest {
    @Autowired
    TranslationRepo translationRepo;

    @Test
    public void testFindByLanguage_exists(){
        TranslationModel model = getBasicTranslationModel();
        translationRepo.save(model);
        assertEquals(translationRepo.findByLanguage(Languages.DE),model);
    }

    @Test
    public void testFindByLanguage_doesNotExists(){
        assertNull(translationRepo.findByLanguage(Languages.DE));
    }

    public TranslationModel getBasicTranslationModel(){
        return new TranslationModel(Languages.DE,"haus");
    }




}
