package dev.lapinski.persinate.example;

import dev.lapinski.persinate.example.cats.Cat;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public class CatTest extends TestBase {
    @Test
    @Transactional
    void insertCat() {
        var cat = new Cat();
        cat.setName("Catty");
        cat.setBirthDate(LocalDate.of(1999, 3, 31));
        entityManagerFactory.createEntityManager().persist(cat);
    }
}
