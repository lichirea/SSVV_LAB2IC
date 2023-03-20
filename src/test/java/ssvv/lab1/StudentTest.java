package ssvv.lab1;

import domain.Student;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.StudentFileRepository;

public class StudentTest {
    validation.StudentValidator studentValidator;

    repository.StudentXMLRepo studentXMLRepository;

    @BeforeEach
    void setUp() {
        this.studentValidator = new validation.StudentValidator();
        String filenameStudent = "fisiere/testStudents.xml";
        this.studentXMLRepository = new repository.StudentXMLRepo(filenameStudent);
    }


    @Test
    @DisplayName("Simple add student should work")
    void testAdd() {
        this.studentXMLRepository.delete("1");
        domain.Student s = new Student("1", "TESTNUME", 55, "a@test.com");
        Assert.assertNull(this.studentXMLRepository.save(s));
    }

    @Test
    @DisplayName("Shouldn't work to add same student twice")
    void testAddTwice() {
        this.studentXMLRepository.delete("1");
        domain.Student s = new Student("1", "TESTNUME", 55, "a@test.com");
        Assert.assertNull(this.studentXMLRepository.save(s));
        Assert.assertEquals(s, this.studentXMLRepository.save(s));
    }
}
