package StreamAPIprod.PerepisNaselenia;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");

        Collection<Person> persons = new ArrayList<>();

        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        long minorsCount = persons.stream()
                .filter(p -> p.getAge() < 18)
                .count();

        List<String> conscriptsFamilys = persons.stream()
                .filter(p -> p.getSex().equals(Sex.MAN))
                .filter(p -> p.getAge() >= 18 && p.getAge() < 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());

        List<Person> workablesWithHigherEducation = persons.stream()
                .filter(p -> p.getEducation().equals(Education.HIGHER))
                .filter(p -> (p.getSex().equals(Sex.WOMAN) && p.getAge() >= 18 && p.getAge() <= 60)
                        || (p.getSex().equals(Sex.MAN) && p.getAge() >= 18 && p.getAge() <= 65))
                .sorted(Comparator.comparing(Person::getFamily).thenComparing(Person::getName))
                .collect(Collectors.toList());

        long otherPeople = (persons.size() - minorsCount - conscriptsFamilys.size() - workablesWithHigherEducation.size());
        System.out.println("Население Лондона: " + persons.size() + " человек");
        System.out.println();
        System.out.println("Несовершеннолетних: " + minorsCount + " человек");
        System.out.println("Призывников: " + conscriptsFamilys.size() + " человек");
        System.out.println("С высшим образованием: " + workablesWithHigherEducation.size() + " человек");
        System.out.println("Остальные: " + otherPeople + " человек");
    }
}