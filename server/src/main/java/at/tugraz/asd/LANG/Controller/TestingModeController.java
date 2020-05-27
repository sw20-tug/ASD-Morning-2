package at.tugraz.asd.LANG.Controller;

import at.tugraz.asd.LANG.Messages.in.SaveTestRequest;
import at.tugraz.asd.LANG.Service.TestingModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/testing_mode")
public class TestingModeController {
    private TestingModeService service;

    @Autowired
    public TestingModeController(TestingModeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity saveTest(@RequestBody SaveTestRequest saveRequest) {
        service.saveTest(saveRequest);
        return ResponseEntity.ok(null);
    }

}
