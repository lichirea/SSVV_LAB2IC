package ssvv.lab1;

import domain.Student;
import domain.Tema;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.StudentFileRepository;
import service.Service;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

import static org.mockito.Mockito.*;

public class IncrementalTest extends TestCase {
    @Mock
    validation.TemaValidator temaValidator;
    @Mock
    repository.TemaXMLRepo temaXMLRepository;
    @Mock
    validation.StudentValidator studentValidator;
    @Mock
    repository.StudentXMLRepo studentXMLRepository;

    @Mock
    validation.NotaValidator gradeValidator;

    @Mock
    repository.NotaXMLRepo gradeXMLRepo;

    @Mock
    service.Service service;



    @BeforeEach
    public void setUp() {
        this.temaValidator = mock(validation.TemaValidator.class);
        String filenameTema = "fisiere/testTema.xml";
        this.temaXMLRepository = mock(repository.TemaXMLRepo.class, withSettings().useConstructor(filenameTema));

        this.studentValidator = mock(validation.StudentValidator.class);
        String filenameStudent = "fisiere/testStudents.xml";
        this.studentXMLRepository = mock(repository.StudentXMLRepo.class, withSettings().useConstructor(filenameStudent));

        this.gradeValidator = mock(validation.NotaValidator.class, withSettings().useConstructor(this.studentXMLRepository, this.temaXMLRepository));
        String filenameGrade = "fisiere/testGrade.xml";
        this.gradeXMLRepo = mock(repository.NotaXMLRepo.class, withSettings().useConstructor(filenameGrade));

        this.service = mock(service.Service.class, withSettings().useConstructor(this.studentXMLRepository, this.studentValidator, this.temaXMLRepository, this.temaValidator, this.gradeXMLRepo, this.gradeValidator));
    }

    @Test
    public void testIncrementalStudent() {
        this.studentXMLRepository.delete("1");
        domain.Student s = new Student("1", "TESTNUME", 55, "a@test.com");
        when(this.studentXMLRepository.save(s)).thenReturn(null);
        when(this.studentXMLRepository.save(s)).thenReturn(s);

    }

    @Test
    public void testIncrementalAssignment() {
        this.studentXMLRepository.delete("1");
        domain.Student s = new Student("1", "TESTNUME", 55, "a@test.com");
        when(this.studentXMLRepository.save(s)).thenReturn(null);
        when(this.studentXMLRepository.save(s)).thenReturn(s);

        this.service.deleteTema("5");

        domain.Tema t = new domain.Tema("5", "descriere", 5, 5);
        int total =  ((Collection<?>) this.service.getAllTeme()).size();

        try {
            when(this.service.addTema(t)).thenReturn(null);
            when(this.service.findTema("5")).thenReturn(t);
        } catch (validation.ValidationException error) {
            Assert.fail();
        }
    }

    @Test
    public void testIncrementalGrade() {
        this.studentXMLRepository.delete("1");
        domain.Student s = new Student("1", "TESTNUME", 55, "a@test.com");
        when(this.studentXMLRepository.save(s)).thenReturn(null);
        when(this.studentXMLRepository.save(s)).thenReturn(s);

        this.service.deleteTema("1");

        domain.Tema t = new domain.Tema("1", "descriere", 6, 5);
        int total =  ((Collection<?>) this.service.getAllTeme()).size();

        try {
            when(this.service.addTema(t)).thenReturn(null);
            when(this.service.findTema("1")).thenReturn(t);
        } catch (validation.ValidationException error) {
            Assert.fail();
        }

        String[] date = "2018-11-10".split("-");
        LocalDate dataPredare = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        domain.Nota n = new domain.Nota("1#1", "1", "1", 10, dataPredare);
        try {
            when(this.service.addNota(n, "GENIAL")).thenReturn( 10.0);
        } catch (validation.ValidationException error) {
            Assert.fail();
        }
    }

}
