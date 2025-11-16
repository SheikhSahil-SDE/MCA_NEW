













# A Sequence Diagram for Online Examination System.

A Sequence Diagram visualizes the order of interactions between objects for a specific scenario, demonstrating the flow of control and data over time.

**1. Scenario**

For the Online Examination System, the core scenario is a "Student Taking and Submitting an Exam".

**Scenario**: A student logs in, selects an exam, attempts the questions, and submits their answers. The system then grades the exam and displays the results.

**2. Lifelines (Objects)**

The objects (or "lifelines") involved in this scenario are:

  * :Student: The actor who takes the exam.
  * :LoginSystem: Handles user authentication.
  * :ExamSystem: A controller object managing exam selection and submission.
  * :Exam: The specific exam instance being taken.
  * :QuestionBank: Stores all available questions.
  * :Result: The object created to store the student's score.

**3. Sequence Diagram for "Student Taking and Submitting an Exam"**

**Diagram Logic & Flow (Mermaid)**

This diagram includes an ```opt``` fragment for a specific action (viewing instructions) and an ```alt``` fragment for the pass/fail result.
```Code snippet```
```
sequenceDiagram
    actor Student
    participant LoginSystem
    participant ExamSystem
    participant Exam
    participant QuestionBank
    participant Result

    %% 1. Student Logs In
    Student->>LoginSystem: 1. login(username, password)
    activate LoginSystem
    LoginSystem-->>Student: 2. return authToken / status
    deactivate LoginSystem

    alt [Login Success]
        %% 3. Student Selects Exam
        Student->>ExamSystem: 3. selectExam(examId, authToken)
        activate ExamSystem

        %% 4. Exam System loads exam details and questions
        ExamSystem->>Exam: 4. loadExamDetails(examId)
        activate Exam
        Exam-->>ExamSystem: 5. return examDetails
        deactivate Exam

        ExamSystem->>QuestionBank: 6. getQuestions(examId)
        activate QuestionBank
        QuestionBank-->>ExamSystem: 7. return examQuestions
        deactivate QuestionBank

        %% 8. Optional: Display Instructions
        opt [Display Instructions]
            ExamSystem-->>Student: 8. displayExamInstructions(examDetails)
        end

        %% 9. Student Starts Exam
        Student->>ExamSystem: 9. startExam()
        ExamSystem-->>Student: 10. displayFirstQuestion()

        loop While questions remain
            Student->>ExamSystem: 11. submitAnswer(qId, selectedOption)
            ExamSystem-->>Student: 12. displayNextQuestion()
        end

        %% 13. Student Submits Exam
        Student->>ExamSystem: 13. submitExam()
        
        %% 14. Exam System Grades Exam
        ExamSystem->>Exam: 14. calculateScore(submittedAnswers)
        activate Exam
        Exam-->>ExamSystem: 15. return finalScore
        deactivate Exam

        %% 16. Exam System Stores Result
        ExamSystem->>Result: 16. createResult(studentId, examId, finalScore)
        activate Result
        Result-->>ExamSystem: 17. return resultId
        deactivate Result

        %% 18. Display Result
        alt [finalScore >= passingMarks]
            ExamSystem-->>Student: 18a. displayResult("Pass", finalScore)
        else [finalScore < passingMarks]
            ExamSystem-->>Student: 18b. displayResult("Fail", finalScore)
        end
        deactivate ExamSystem
    else [Login Failed]
        LoginSystem-->>Student: 3. displayErrorMessage("Invalid Credentials")
    end
```


**4. Step-by-Step Explanation**

1. ```login()```: The ```Student``` sends their credentials to the ```:LoginSystem```.
2. ```return authToken / status```: The ```:LoginSystem``` responds with either a success token or a failure message.
3. ```alt [Login Success]```: This fragment handles the two paths:

   * Success Path:
     * ```selectExam()```: ```Student``` chooses an exam.
     * ```loadExamDetails()``` & ```getQuestions()```: The :ExamSystem loads exam rules and questions from the :Exam and :QuestionBank objects.
     * ```opt [Display Instructions]```: An optional step where instructions might be shown.
     * startExam(): Student starts the exam.
     * loop: The Student continuously submits answers, and the system displays the next question.
     * submitExam(): After all questions, Student submits.
     * calculateScore(): The :ExamSystem asks the :Exam object to calculate the score.
     * createResult(): The :ExamSystem then creates a :Result object to store the score.
     * alt [finalScore ...]: The system displays "Pass" or "Fail" based on the score.
   * Failure Path: The :LoginSystem shows an error message.
