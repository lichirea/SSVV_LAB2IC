package ssvv.lab1;

import domain.Student;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.StudentFileRepository;

public class StudentTest extends TestCase{
    validation.StudentValidator studentValidator;

    repository.StudentXMLRepo studentXMLRepository;

    @BeforeEach
     public void setUp() {
        this.studentValidator = new validation.StudentValidator();
        String filenameStudent = "fisiere/testStudents.xml";
        this.studentXMLRepository = new repository.StudentXMLRepo(filenameStudent);
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
}
