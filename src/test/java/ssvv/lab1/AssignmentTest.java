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

import java.util.Collection;

public class AssignmentTest extends TestCase{
    validation.TemaValidator temaValidator;

    repository.TemaXMLRepo temaXMLRepository;

    service.Service service;

    @BeforeEach
    public void setUp() {
        this.temaValidator = new validation.TemaValidator();
        String filenameTema = "fisiere/testTema.xml";
        this.temaXMLRepository = new repository.TemaXMLRepo(filenameTema);
        this.service = new Service(null, null, this.temaXMLRepository, this.temaValidator, null, null);
    }
    @Test
    public void testDeadlineValidation() {
        this.service.deleteTema("5");

        domain.Tema t = new domain.Tema("5", "nume", 15, 1);
        int total =  ((Collection<?>) this.service.getAllTeme()).size();

        try {
            this.service.addTema(t);
            Assert.fail();
        } catch (validation.ValidationException error) {
            Assert.assertNull(this.service.findTema("5"));
            Assert.assertEquals(total, ((Collection<?>) this.service.getAllTeme()).size());
            Assert.assertEquals(error.getMessage(), "Deadlineul trebuie sa fie intre 1-14.");
        }

        t = new domain.Tema("5", "nume", 0, 1);
        total =  ((Collection<?>) this.service.getAllTeme()).size();

        try {
            this.service.addTema(t);
            Assert.fail();
        } catch (validation.ValidationException error) {
            Assert.assertNull(this.service.findTema("5"));
            Assert.assertEquals(total, ((Collection<?>) this.service.getAllTeme()).size());
            Assert.assertEquals(error.getMessage(), "Deadlineul trebuie sa fie intre 1-14.");
        }

        t = new domain.Tema("5", "nume", 5, 1);
        total =  ((Collection<?>) this.service.getAllTeme()).size();

        try {
            Assert.assertNull(this.service.addTema(t));
            Assert.assertEquals(total + 1, ((Collection<?>) this.service.getAllTeme()).size());
            Assert.assertEquals(t, this.service.findTema("5")); ;
        } catch (validation.ValidationException error) {
            Assert.fail();
        }
    }

    @Test
    public void testIdValidation() {
        this.service.deleteTema("5");

        domain.Tema t = new domain.Tema("", "nume", 5, 1);
        int total =  ((Collection<?>) this.service.getAllTeme()).size();

        try {
            this.service.addTema(t);
            Assert.fail();
        } catch (validation.ValidationException error) {
            Assert.assertEquals(total, ((Collection<?>) this.service.getAllTeme()).size());
            Assert.assertEquals(error.getMessage(), "Numar tema invalid!");
        }

        t = new domain.Tema(null, "nume", 5, 1);
        total =  ((Collection<?>) this.service.getAllTeme()).size();

        try {
            this.service.addTema(t);
            Assert.fail();
        } catch (validation.ValidationException error) {
            Assert.assertEquals(total, ((Collection<?>) this.service.getAllTeme()).size());
            Assert.assertEquals(error.getMessage(), "Numar tema invalid!");
        }

        t = new domain.Tema("5", "nume", 5, 1);
        total =  ((Collection<?>) this.service.getAllTeme()).size();

        try {
            Assert.assertNull(this.service.addTema(t));
            Assert.assertEquals(total + 1, ((Collection<?>) this.service.getAllTeme()).size());
            Assert.assertEquals(t, this.service.findTema("5")); ;
        } catch (validation.ValidationException error) {
            Assert.fail();
        }
    }
}
