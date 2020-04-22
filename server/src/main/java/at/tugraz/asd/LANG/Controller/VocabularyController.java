package at.tugraz.asd.LANG.Controller;


import at.tugraz.asd.LANG.Messages.in.CreateVocabularyMessageIn;
import at.tugraz.asd.LANG.Messages.in.EditVocabularyMessageIn;
import at.tugraz.asd.LANG.Messages.out.VocabularyOut;
import at.tugraz.asd.LANG.Model.VocabularyModel;
import at.tugraz.asd.LANG.Service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vocabulary")
public class VocabularyController {

    @Autowired
    VocabularyService service;

    @PostMapping
    public ResponseEntity addVocabulary(@RequestBody CreateVocabularyMessageIn msg){
       service.saveVocabulary(msg);
       return ResponseEntity.ok(null);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity getAllVocabulary(){
        ArrayList<VocabularyOut> ret = new ArrayList<>();
        List<VocabularyModel> vocab = service.getAllVocabulary();
        if(vocab.isEmpty())
            return ResponseEntity.noContent().build();
        vocab.forEach(el->{
            ret.add(new VocabularyOut(
                    el.getTopic(),
                    el.getVocabulary(),
                    null
                    //el.getTranslations().keySet()
            ));
        });
        return ResponseEntity.ok(ret);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity editVocabulary(@RequestBody EditVocabularyMessageIn msg)
    {
        int ret = service.editVocabulary(msg);
        if(ret == 1)
        {
            return ResponseEntity.ok(null);
        }
        else
        {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
