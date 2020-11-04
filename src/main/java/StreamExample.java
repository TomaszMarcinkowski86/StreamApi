import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamExample {
    List<Employee> employees=new ArrayList<>();

    @BeforeEach
    public void setUp(){
        Employee employee1 =new Employee("Tomasz","Marcinkowski",34, Arrays.asList("Java", "JavaScript","Phyton"));
        Employee employee2 =new Employee("Maciej","Mirczak",45, Arrays.asList("Java", "JavaScript","Phyton","PHP"));
        Employee employee3 =new Employee("Gosia","Pandaazrobie",40, Arrays.asList("Java", "C#","C++"));
        Employee employee4 =new Employee("Andrzej","Suchar",60, Arrays.asList("Java", "JavaScript","Phyton"));
        Employee employee5 =new Employee("Krzysiek","Kowalski",22, Arrays.asList("C", "C++"));
        Employee employee6 =new Employee("Jurek","Kozowal",55, Arrays.asList("PHP", "React","Angular"));
        Employee employee7 =new Employee("Piotrek","Grab",23, Arrays.asList("Java", "Spring","Hibernate"));
        Employee employee8 =new Employee("Mariusz","Szulca",30, Arrays.asList("Rust","Phyton"));
        Employee employee9 =new Employee("Lukasz","Sewerynka",48, Arrays.asList("Java", "Phyton"));
        Employee employee10 =new Employee("Magda","Drobniak",18, Arrays.asList("Java", "Scala"));

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        employees.add(employee5);
        employees.add(employee6);
        employees.add(employee7);
        employees.add(employee8);
        employees.add(employee9);
        employees.add(employee10);

    }
    @Test
    public void firstStream(){
        employees.stream()
                .forEach(emp->System.out.println(emp));
    }
    @Test
    public void theSameAsUpper(){
        employees //mozna bez .stream()
                .forEach(System.out::println); //referencja do metody
    }
    @Test
    public void mapOperation(){
        employees.stream()
//                .map(emp->emp.getFirstName()) to można zastapić referencją do metody
               .map(Employee::getFirstName)
                .forEach(System.out::println);
    }
    @Test
    public void mapOperation2(){
        employees.stream()
             .map(emp->emp.getFirstName() + " "+ emp.getLastName())
                .forEach(System.out::println);
    }
    @Test
    public void flatMapOperation(){
        List<List<String>> allSkills = employees.stream()
                .map(Employee::getSkills)
                .collect(Collectors.toList());

        System.out.println("Lista list przy użyciu map: " + allSkills);

        List<String> allSkills2 = employees.stream()
                .map(Employee::getSkills)
                .flatMap(list -> list.stream())
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Lista  przy użyciu flatMap: " + allSkills2);

    }

    @Test
    public void filterOperation(){
        employees.stream()
                .filter(emp->emp.getFirstName().startsWith("M"))
                .forEach(System.out::println);
    }
    @Test
    public void sortedOperation(){
        employees.stream()
                .sorted(Comparator.comparing(Employee::getAge))
                .forEach(System.out::println);
    }
    @Test
    public void sortedOperation2(){
        employees.stream()
                .sorted(Comparator.comparing(Employee::getLastName))
                .forEach(System.out::println);
    }

    @Test
    public void limitOperation(){
        employees.stream()
                .sorted(Comparator.comparing(Employee::getLastName))
                .limit(2).forEach(System.out::println);
    }
    @Test
    public void skipOperation(){
        employees.stream()
                .sorted(Comparator.comparing(Employee::getLastName))
                .skip(2)
                .forEach(System.out::println);
    }
    @Test
    public void countOperation(){
        System.out.println(employees.stream().count());

        long numberOfEmpStartsWithM = employees.stream()
                .filter(employee -> employee.getFirstName().startsWith("M"))
                .count();
        System.out.println("Liczba osób na M: "+numberOfEmpStartsWithM);

    }
    @Test
    public void minMaxOperation(){

        Employee youngestEmployee = employees.stream().min(Comparator.comparing(Employee::getAge))
                .get();

        Employee oldestEmployee = employees.stream().max(Comparator.comparing(Employee::getAge))
                .get();
        System.out.println("Youngest employee: " + youngestEmployee);
        System.out.println("Oldest employee: " + oldestEmployee);

        String oldestEmployeeLastName = employees.stream()
                .max(Comparator.comparing(Employee::getAge))
                .map(Employee::getLastName).get();

        System.out.println("Nazwisko najstarszego: " + oldestEmployeeLastName);

    }

    @Test
    public void findAnyFindFirstOperation(){
        Employee firstNameStartWithM = employees.stream()
                .filter(employee -> employee.getFirstName().startsWith("M"))
                .findFirst().get();
        System.out.println(firstNameStartWithM);

        Employee firstAnyStartWithM = employees.stream()
                .filter(employee -> employee.getFirstName().startsWith("M"))
                .findAny().get();
        System.out.println(firstAnyStartWithM);
    }

    @Test
    public void matchOperation(){
        boolean a = employees.stream()
                .allMatch(emp -> emp.getFirstName().startsWith("T"));
        System.out.println("Czy imiona wszystkich zaczynają się na T? "+a);

        boolean b = employees.stream()
                .allMatch(emp -> emp.getLastName().contains("a"));
        System.out.println("Czy nazwiska wszystkich zawierają litere a? "+b);

        boolean c = employees.stream()
                .anyMatch(emp -> emp.getFirstName().startsWith("T"));
        System.out.println("Czy mamy jakieś imię na T? "+c);

        boolean d = employees.stream()
                .noneMatch(emp -> emp.getFirstName().startsWith("T"));
        System.out.println("Czy nie ma imienia na T? "+a);
    }

    @Test
    public void reduceOperation(){
        Integer sumOfAllAges = employees.stream()
                .map(Employee::getAge)
                .reduce((age1, age2) -> age1 + age2)
                .get();

        System.out.println("wiek wszystkich to: " + sumOfAllAges);

        Integer sumOfAllAges2 = employees.stream()
                .map(Employee::getAge)
                .reduce(Integer::sum)
                .get();

        System.out.println("wiek wszystkich przy pomocy ref do metody: " + sumOfAllAges2);

        Integer sumOfAllAges3 = employees.stream()
                .map(Employee::getAge)
                .reduce(0,Integer::sum); // zero to domyślna wartość nie index

        System.out.println("wiek wszystkich a domyślna wartość to 0: " + sumOfAllAges3);

        Integer sumOfAllAges4 = employees.stream()
                .reduce(0,(age,emp)->age+emp.getAge(),Integer::sum);

        System.out.println("wiek wszystkich bez mapowania: " + sumOfAllAges4);

        String nameWithseparate = employees.stream()
                .map(Employee::getFirstName)
                .reduce((name, name2) -> name + ", " + name2)
                .get();

        System.out.println(nameWithseparate);

    }

/** w Java 8 nie działa od 9 chyba
 *  @Test
 *     public  void takeWhileOperation(){
 *         employees.stream()
 *                 .sorted(Comparator.comparing(Employee::getAge))
 *                 .takeWhile(emp->emp.getAge()<30)
 *                 .foreach(System.out::printf);
 *
 *        @Test
 *  *     public  void dropWhileOperation(){
 *  *         employees.stream()
 *  *                 .sorted(Comparator.comparing(Employee::getAge))
 *  *                 .dropWhile(emp->emp.getAge()<30)
 *  *                 .foreach(System.out::printf);
 *
 */


    @Test
    public void forEachOrdered(){
        String sentence = " Jak nauczyć się programować";

       // sentence.chars().forEach(s->System.out.println((char) s));
        sentence.chars().forEach(s->System.out.print((char) s));
        System.out.println();
        System.out.println("wielowątkowo");
        sentence.chars().parallel().forEach(s->System.out.print((char) s));
        System.out.println();
        System.out.println("wielowątkowo ale forEachOrdered");
        sentence.chars().parallel().forEachOrdered(s->System.out.print((char) s));
    }

    //peek służy do modyfikacji oryginalnej kolekcji więc nie używamy tego :)
    // wprowadzili do sprawdzania podglądania elementów
    @Test
    public void peekOperation(){

        List<Employee> newEmployees = employees.stream()
                .peek(employee -> employee.setFirstName("Tomek"))
                .collect(Collectors.toList());

        System.out.println(newEmployees);
        System.out.println("zmodyfikowana oryginalna kolekcja ");
        System.out.println(employees);


    }


}



