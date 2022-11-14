package com.example.backtest.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:db-test.properties")
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void GivenExistingTitle_WhenExists_ThenShouldBeTrue(){
        // Given
        final String title = "El padrino";

        // When
        final Boolean exists = this.movieRepository.exists(title);

        // Then
        assertThat(exists, is(Boolean.TRUE));
    }

    @Test
    public void GivenNonExistingTitle_WhenNonExists_ThenShouldBeFalse(){
        // Given
        final String title = "La madrina";

        // When
        final Boolean exists = this.movieRepository.exists(title);

        // Then
        assertThat(exists, is(Boolean.FALSE));
    }
}
