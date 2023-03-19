package com.sources.repository;

import com.sources.model.entity.bar.BarEntity;
import com.sources.model.entity.foo.FooEntity;
import com.sources.repository.bar.BarRepository;
import com.sources.repository.foo.FooRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class MultipleDataSourceIntegrationTest {

    @Autowired
    private FooRepository fooRepository;

    @Autowired
    private BarRepository barRepository;

    @Test
    void testFooRepositoryAdd3Entities() {
        final int ROWS = 3;

        IntStream.rangeClosed(1, ROWS).forEach(i -> fooRepository.save(new FooEntity("Foo Test Content " + i)));
        IntStream.rangeClosed(1, ROWS).forEach(i -> barRepository.save(new BarEntity("Bar Test Content " + i)));

        assertEquals(ROWS, fooRepository.count(), "Expected 3 rows in \"foos\" table");
        assertEquals(ROWS, barRepository.count(), "Expected 3 rows in \"bars\" table");

        final Long ID = 3L;

        Optional<FooEntity> fooById = fooRepository.findById(ID);

        assertAll(
                () -> assertTrue(fooById.isPresent(), "Expected row with id=3 to be present in foos"),
                () -> assertEquals("Foo Test Content 3", fooById.get().getFooContent(),
                        "Expected fooContent property of row with \"id=3\" to be \"Foo Test Content 3\"")
        );

        Optional<BarEntity> barById = barRepository.findById(ID);
        assertAll(
                () -> assertTrue(barById.isPresent(), "Expected row with id=3 to be present in foos"),
                () -> assertEquals("Bar Test Content 3", barById.get().getBarContent(),
                        "Expected fooContent property of row with \"id=3\" to be \"Bar Test Content 3\"")
        );

        fooRepository.deleteAll();
        barRepository.deleteAll();
    }

}


