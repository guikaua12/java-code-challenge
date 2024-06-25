package me.approximations.javacodechallenge.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of="id")
@NoArgsConstructor(force=true)
@Entity
@Table(name="departamentos")
public class Departamento {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private final Long id;
    private String name;
}
