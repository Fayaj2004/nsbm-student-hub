package lk.nsbm.studenthub.service;

import lk.nsbm.studenthub.dto.StudentRequest;
import lk.nsbm.studenthub.dto.StudentResponse;
import lk.nsbm.studenthub.entity.Student;
import lk.nsbm.studenthub.repository.StudentRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public StudentResponse create(StudentRequest req) {
        if (repo.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already exists: " + req.email());
        }
        Student s = Student.builder()
                .name(req.name())
                .email(req.email())
                .batch(req.batch())
                .gpa(req.gpa())
                .build();
        return toResponse(repo.save(s));
    }

    public Page<StudentResponse> getAll(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(sortBy == null || sortBy.isBlank() ? "id" : sortBy);
        sort = "desc".equalsIgnoreCase(direction) ? sort.descending() : sort.ascending();

        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1), sort);
        return repo.findAll(pageable).map(this::toResponse);
    }

    public StudentResponse getById(Long id) {
        Student s = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found: " + id));
        return toResponse(s);
    }

    @Transactional
    public StudentResponse update(Long id, StudentRequest req) {
        Student s = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found: " + id));

        // if email changed, ensure unique
        if (!s.getEmail().equalsIgnoreCase(req.email()) && repo.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already exists: " + req.email());
        }

        s.setName(req.name());
        s.setEmail(req.email());
        s.setBatch(req.batch());
        s.setGpa(req.gpa());

        return toResponse(repo.save(s));
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Student not found: " + id);
        }
        repo.deleteById(id);
    }

    private StudentResponse toResponse(Student s) {
        return new StudentResponse(s.getId(), s.getName(), s.getEmail(), s.getBatch(), s.getGpa());
    }
}
