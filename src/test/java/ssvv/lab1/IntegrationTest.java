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

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

public class IntegrationTest extends TestCase {
    validation.TemaValidator temaValidator;

    repository.TemaXMLRepo temaXMLRepository;

    validation.StudentValidator studentValidator;

    repository.StudentXMLRepo studentXMLRepository;

    validation.NotaValidator gradeValidator;

    repository.NotaXMLRepo gradeXMLRepo;

    service.Service service;

    @BeforeEach
    public void setUp() {
        this.temaValidator = new validation.TemaValidator();
        String filenameTema = "fisiere/testTema.xml";
        this.temaXMLRepository = new repository.TemaXMLRepo(filenameTema);

        this.studentValidator = new validation.StudentValidator();
        String filenameStudent = "fisiere/testStudents.xml";
        this.studentXMLRepository = new repository.StudentXMLRepo(filenameStudent);

        this.gradeValidator = new validation.NotaValidator(this.studentXMLRepository, this.temaXMLRepository);
        String filenameGrade = "fisiere/testGrade.xml";
        this.gradeXMLRepo = new repository.NotaXMLRepo(filenameGrade);

        this.service = new service.Service(this.studentXMLRepository, this.studentValidator, this.temaXMLRepository, this.temaValidator, this.gradeXMLRepo, this.gradeValidator);
    }

    @Test
    public void testIntegrationAssignment() {
        this.service.deleteTema("5");

        domain.Tema t = new domain.Tema("5", "descriere", 5, 5);
        int total =  ((Collection<?>) this.service.getAllTeme()).size();

        try {
            Assert.assertNull(this.service.addTema(t));
            Assert.assertEquals(total + 1, ((Collection<?>) this.service.getAllTeme()).size());
            Assert.assertEquals(t, this.service.findTema("5")); ;
        } catch (validation.ValidationException error) {
            Assert.fail();

        }
    }

    @Test
    public void testIntegrationStudent() {
        this.studentXMLRepository.delete("1");
        domain.Student s = new Student("1", "TESTNUME", 55, "a@test.com");
        Assert.assertNull(this.studentXMLRepository.save(s));
        Assert.assertEquals(s, this.studentXMLRepository.save(s));
    }

    @Test
    public void testIntegrationGrade() {

        domain.Nota n = new domain.Nota("5#5", "5555", "5555", 15, null);

        try {
            this.service.addNota(n, "GENIAL");
            Assert.fail();
        } catch (validation.ValidationException error) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testTripleIntegration() {
        this.service.deleteTema("1");
        domain.Tema t = new domain.Tema("1", "descriere", 6, 5);
        int total =  ((Collection<?>) this.service.getAllTeme()).size();
        try {
            Assert.assertNull(this.service.addTema(t));
            Assert.assertEquals(total + 1, ((Collection<?>) this.service.getAllTeme()).size());
            Assert.assertEquals(t, this.service.findTema("1")); ;
        } catch (validation.ValidationException error) {
            Assert.fail();
        }

        this.studentXMLRepository.delete("1");
        domain.Student s = new Student("1", "TESTNUME", 55, "a@test.com");
        Assert.assertNull(this.studentXMLRepository.save(s));
        Assert.assertEquals(s, this.studentXMLRepository.save(s));

        String[] date = "2018-11-10".split("-");
        LocalDate dataPredare = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        domain.Nota n = new domain.Nota("1#1", "1", "1", 10, dataPredare);
        try {
            Assert.assertEquals(10.0, this.service.addNota(n, "GENIAL"));
        } catch (validation.ValidationException error) {
            Assert.fail();
        }

    }
}
