package lk.nsbm.studenthub.dto;

public record StudentResponse(
        Long id,
        String name,
        String email,
        String batch,
        Double gpa
) {}
