package at.tugraz.asd.LANG.Messages.out;

import at.tugraz.asd.LANG.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor

public class GetAllTopicsOut {
    private List<Topic> topics;
}
