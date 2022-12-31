package com.example.library.studentlibrary.services;

import com.example.library.studentlibrary.models.Book;
import com.example.library.studentlibrary.models.Card;
import com.example.library.studentlibrary.models.Student;
import com.example.library.studentlibrary.repositories.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {


    @Autowired
    CardService cardService4;

    @Autowired
    StudentRepository studentRepository4;

    public Student getDetailsByEmail(String email){
        Student student = studentRepository4.findByEmailId(email);

        return student;
    }

    public Student getDetailsById(int id){
        Student student = studentRepository4.findById(id).get();

        return student;
    }

    public void createStudent(Student student){
        studentRepository4.save(student);
        Card card = cardService4.createAndReturn(student);
    }

    public void updateStudent(Student student){
//        Student oldStudent = studentRepository4.findById(student.getId()).get();
//        oldStudent.setEmailId(student.getEmailId());
//        oldStudent.setName(student.getName());
//        oldStudent.setAge(student.getAge());
//        oldStudent.setCountry(student.getCountry());
        studentRepository4.updateStudentDetails(student);

    }

    public void deleteStudent(int id){
        //Delete student and deactivate corresponding card
        Student student = studentRepository4.findById(id).get();
        Card card = student.getCard();
        List<Book> bookList=card.getBooks();
        for(Book temp:bookList){
            temp.setAvailable(true);
        }
        cardService4.deactivateCard(student.getId());
        studentRepository4.deleteCustom(id);
    }
}