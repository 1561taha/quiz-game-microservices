package com.taha.question_service.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String questionTitle;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "questionDb_id"))
    private List<String> options;

    @Column(nullable = false)
    private  Long correctOption;

    @Column(nullable = false)
    private  String category;

    @Column(nullable = false)
    private String difficulty;

}
