package at.tugraz.asd.LANG.Controller;

import at.tugraz.asd.LANG.Service.TestingModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/testing_mode")
public class TestingModeController {
    private final TestingModeService service;

    @Autowired
    public TestingModeController(TestingModeService service) { this.service = service; }

    @PostMapping (path = "/save")
    public ResponseEntity saveTest(@RequestBody String testStates) {
        service.saveTest(testStates);
        return ResponseEntity.ok(null);
    }

    @GetMapping (path = "/continue")
    public ResponseEntity getTest() { return ResponseEntity.ok(service.getTest()); }

    @GetMapping (path = "/check")
    public ResponseEntity checkForSavedTest() { return ResponseEntity.ok(service.checkForSavedTest()); }

}
