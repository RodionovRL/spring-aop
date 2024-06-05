package ru.t1.solution.model;

import jakarta.persistence.*;
import lombok.*;
import ru.t1.owner.model.Owner;

@Getter
@Setter
@Entity
@Table(name = "solutions")
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "version")
    private String version;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Owner owner;
}