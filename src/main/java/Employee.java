import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Employee {

        private String firstName;
        private String lastName;
        int age;
        private List<String> skills;

    }
