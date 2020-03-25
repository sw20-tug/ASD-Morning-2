package at.tugraz.asd.LANG.Controller;


import at.tugraz.asd.LANG.Messages.in.CreateVocabularyMessageIn;
import at.tugraz.asd.LANG.Messages.out.VocabularyOut;
import at.tugraz.asd.LANG.Model.VocabularyModel;
import at.tugraz.asd.LANG.Service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vocabulary")
public class VocabularyController {

    @Autowired
    VocabularyService service;

    @PostMapping
    public void addVocabulary(@RequestBody CreateVocabularyMessageIn msg){
       service.saveVocabulary(msg);
    }

    @GetMapping
    @ResponseBody
    public ArrayList<VocabularyOut> getAllVocabulary(){
        ArrayList<VocabularyOut> ret = new ArrayList<>();
        List<VocabularyModel> vocab = service.getAllVocabulary();
        vocab.forEach(el->{
            ret.add(new VocabularyOut(
                    el.getTopic(),
                    el.getVocabulary(),
                    null
                    //el.getTranslations().keySet()
            ));
        });
        return ret;
    }


}
