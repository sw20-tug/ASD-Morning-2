package at.tugraz.asd.LANG.Controller;


import at.tugraz.asd.LANG.Exeptions.CreateVocabularyFail;
import at.tugraz.asd.LANG.Exeptions.EditFail;
import at.tugraz.asd.LANG.Languages;
import at.tugraz.asd.LANG.Messages.in.CreateVocabularyMessageIn;
import at.tugraz.asd.LANG.Messages.in.EditVocabularyMessageIn;
import at.tugraz.asd.LANG.Messages.in.ShareMessageIn;
import at.tugraz.asd.LANG.Messages.out.TranslationOut;
import at.tugraz.asd.LANG.Messages.out.VocabularyLanguageOut;
import at.tugraz.asd.LANG.Messages.out.VocabularyOut;
import at.tugraz.asd.LANG.Model.VocabularyModel;
import at.tugraz.asd.LANG.Service.VocabularyService;
import org.apache.logging.log4j.util.PropertySource;
import org.hibernate.usertype.UserVersionType;
import at.tugraz.asd.LANG.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/vocabulary")
public class VocabularyController {

    @Autowired
    VocabularyService service;

    @PostMapping
    public ResponseEntity addVocabulary(@RequestBody CreateVocabularyMessageIn msg){
        try{
            service.saveVocabulary(msg);
            return ResponseEntity.ok(null);
        }catch (CreateVocabularyFail e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping (path = "/share")
    public ResponseEntity shareVocabs(@RequestBody ShareMessageIn msg) throws MessagingException, IOException {
        try
        {
            Boolean ret = service.shareVocab(msg, service.createCSSforShare(msg.getVocabs()));
            return ResponseEntity.ok().body(ret);
        } catch (Exception e)
        {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping (path = "/topics")
    @ResponseBody
    public ResponseEntity getAllTopics()
    {
        return ResponseEntity.ok(service.getAllTopics());
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity getAllVocabulary(){
        ArrayList<VocabularyOut> ret = new ArrayList<>();
        List<VocabularyModel> vocab = service.getAllVocabulary();
        if(vocab.isEmpty())
            return ResponseEntity.noContent().build();
        vocab.forEach(el->{
            HashMap<Languages, String> translation = new HashMap<>();
            el.getTranslationVocabMapping().forEach(translationModel -> {
                translation.put(translationModel.getLanguage(), translationModel.getVocabulary());
            });
            ret.add(new VocabularyOut(
                    el.getTopic(),
                    el.getVocabulary(),
                    translation,
                    el.getRating()
            ));
        });
        return ResponseEntity.ok(ret);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity editVocabulary(@RequestBody EditVocabularyMessageIn msg){
        try{
            service.editVocabulary(msg);
            return ResponseEntity.ok(null);
        }
        catch (EditFail e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping (path = "rating/{aORz}")
    @ResponseBody
    public ResponseEntity getSortedRating(@PathVariable("aORz")String aOrz){
        ArrayList<VocabularyOut> ret = new ArrayList<>();
        List<VocabularyModel> vocab = service.sortRating(aOrz);
        if(vocab.isEmpty())
            return ResponseEntity.noContent().build();

        vocab.forEach(el->{
            HashMap<Languages, String> translation = new HashMap<>();
            el.getTranslationVocabMapping().forEach(translationModel -> {
                translation.put(translationModel.getLanguage(), translationModel.getVocabulary());
            });
            ret.add(new VocabularyOut(
                    el.getTopic(),
                    el.getVocabulary(),
                    translation,
                    el.getRating()
            ));
        });
        return ResponseEntity.ok(ret);
    }

    @GetMapping (path = "alphabetically/{aORz}")
    @ResponseBody
    public ResponseEntity getAllVocabularyAlphabetically1(@PathVariable("aORz")String aOrz){
        ArrayList<VocabularyOut> ret = new ArrayList<>();
        List<VocabularyModel> vocab = service.sortVocabOverview(aOrz);
        if(vocab.isEmpty())
            return ResponseEntity.noContent().build();

        vocab.forEach(el->{
            HashMap<Languages, String> translation = new HashMap<>();
            el.getTranslationVocabMapping().forEach(translationModel -> {
                translation.put(translationModel.getLanguage(), translationModel.getVocabulary());
            });
            ret.add(new VocabularyOut(
                    el.getTopic(),
                    el.getVocabulary(),
                    translation,
                    el.getRating()
            ));
        });
        return ResponseEntity.ok(ret);
    }

    @GetMapping (path = "alphabetically/{Language}/{aORz}")
    @ResponseBody
    public ResponseEntity getAllVocabularyAlphabetically2(@PathVariable("Language")Languages language, @PathVariable("aORz")String aOrz){
        ArrayList<VocabularyOut> ret = new ArrayList<>();
        List<VocabularyModel> vocab = service.sortVocabStudyInterface(language,aOrz);
        if(vocab.isEmpty())
            return ResponseEntity.noContent().build();

        vocab.forEach(el->{
            HashMap<Languages, String> translation = new HashMap<>();
            el.getTranslationVocabMapping().forEach(translationModel -> {
                translation.put(translationModel.getLanguage(), translationModel.getVocabulary());
            });
            ret.add(new VocabularyOut(
                    el.getTopic(),
                    el.getVocabulary(),
                    translation,
                    el.getRating()
            ));
        });
        return ResponseEntity.ok(ret);
    }

    @GetMapping (path = "random")
    @ResponseBody
    public ResponseEntity getRandomVocabulary() {
        int testSize = 10;      // change if test size varies in future issues
        ArrayList<VocabularyOut> ret = new ArrayList<>();
        List<VocabularyModel> randomVocab = new ArrayList<>();
        Random rand = new Random();

        List<VocabularyModel> vocab = service.getAllVocabulary();

        // Check if vocab list exists and has enough vocabs
        if(vocab.isEmpty() || vocab.size() < testSize)
            return ResponseEntity.noContent().build();

        // Select x amount of random vocabs from vocab list, and remove element from vocab list to avoid duplicates
        for (int i = 0; i < testSize; i++) {
            VocabularyModel randomVocabItem = vocab.get(rand.nextInt(vocab.size()));
            randomVocab.add(randomVocabItem);
            vocab.remove(randomVocabItem);
        }
        System.out.println("Random Vocabs are: " + randomVocab.toString());

        // Build response
        randomVocab.forEach(el->{
            HashMap<Languages, String> translation = new HashMap<>();
            el.getTranslationVocabMapping().forEach(
                    translationModel -> translation.put(translationModel.getLanguage(), translationModel.getVocabulary())
            );
            ret.add(new VocabularyOut(
                    el.getTopic(),
                    el.getVocabulary(),
                    translation,
                    el.getRating()

            ));
        });

        return ResponseEntity.ok(ret);
    }

    @GetMapping (path = "sort_topics/{Topic}")
    @ResponseBody
    public ResponseEntity sortByTopic(@PathVariable("Topic") Topic msg)
    {
        ArrayList<VocabularyOut> ret = new ArrayList<>();
        List<VocabularyModel> vocab = service.sortTopics(msg);

        if(vocab.isEmpty())
            return ResponseEntity.noContent().build();

        vocab.forEach(el->{
            HashMap<Languages, String> translation = new HashMap<>();
            el.getTranslationVocabMapping().forEach(translationModel -> {
                translation.put(translationModel.getLanguage(), translationModel.getVocabulary());
            });
            ret.add(new VocabularyOut(
                    el.getTopic(),
                    el.getVocabulary(),
                    translation,
                    el.getRating()
            ));
        });

        return ResponseEntity.ok(ret);
    }

    @GetMapping  (path = "Export")
    public ResponseEntity exportBackup(){
        try{
            File backup = service.exportVocabulary();
            Path path = Paths.get(backup.getPath());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

            return ResponseEntity.ok()
                    .contentLength(backup.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        catch (Exception e){
            System.out.println("Error exporting File " + e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping (path = "Import")

    public ResponseEntity importBackup(@RequestParam("file") MultipartFile Backup_File){
        try{
            String content = new String(Backup_File.getBytes());
            Boolean success = service.importVocabulary(content);

            return ResponseEntity.ok()
                    .body(success);
        }
        catch (Exception e){
            System.out.println("Error Importing File " + e);
            return ResponseEntity.badRequest().body(null);
        }
    }


}
