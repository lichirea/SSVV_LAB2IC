package ssvv.lab1;

import domain.Student;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.StudentFileRepository;
import service.Service;

public class StudentTest extends TestCase{
    validation.StudentValidator studentValidator;

    repository.StudentXMLRepo studentXMLRepository;

    service.Service service;

    @BeforeEach
     public void setUp() {
        this.studentValidator = new validation.StudentValidator();
        String filenameStudent = "fisiere/testStudents.xml";
        this.studentXMLRepository = new repository.StudentXMLRepo(filenameStudent);
        this.service = new Service(this.studentXMLRepository, this.studentValidator, null, null, null, null);

    }

    @Test
    public void testAdd() {
        this.studentXMLRepository.delete("1");
        domain.Student s = new Student("1", "TESTNUME", 55, "a@test.com");
        Assert.assertNull(this.studentXMLRepository.save(s));
    }

    @Test
    public void testAddTwice() {
        this.studentXMLRepository.delete("1");
        domain.Student s = new Student("1", "TESTNUME", 55, "a@test.com");
        Assert.assertNull(this.studentXMLRepository.save(s));
        Assert.assertEquals(s, this.studentXMLRepository.save(s));
    }

    @Test
    public void testIdNull() {
        domain.Student s = new Student(null, "TESTNUME", 55, "a@test.com");
        try {
            this.service.addStudent(s);
            Assert.fail();
        } catch (validation.ValidationException ignored) {
        }
    }
    @Test
    public void testIdEmptyString() {
        domain.Student s = new Student("", "TESTNUME", 55, "a@test.com");
        try {
            this.service.addStudent(s);
            Assert.fail();
        } catch (validation.ValidationException ignored) {
        }
    }
    @Test
    public void testNameEmptyString() {
        domain.Student s = new Student("1", "", 55, "a@test.com");
        try {
            this.service.addStudent(s);
            Assert.fail();
        } catch (validation.ValidationException ignored) {
        }
    }

    @Test
    public void testNameNull() {
        domain.Student s = new Student("1", null, 55, "a@test.com");
        try {
            this.service.addStudent(s);
            Assert.fail();
        } catch (validation.ValidationException ignored) {
        }
    }
    @Test
    public void testGroupNegative() {
        domain.Student s = new Student("1", "TESTNUME", -55, "a@test.com");
        try {
            this.service.addStudent(s);
            Assert.fail();
        } catch (validation.ValidationException ignored) {
        }
    }
    @Test
    public void testEmailNull() {
        domain.Student s = new Student("1", "TESTNUME", 55, null);
        try {
            this.service.addStudent(s);
            Assert.fail();
        } catch (validation.ValidationException ignored) {
        }
    }
    @Test
    public void testEmailEmptyString() {
        domain.Student s = new Student("1", "TESTNUME", 55, "");
        try {
            this.service.addStudent(s);
            Assert.fail();
        } catch (validation.ValidationException ignored) {
        }
    }
}
