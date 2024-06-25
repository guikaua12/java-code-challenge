package me.approximations.javacodechallenge.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.approximations.javacodechallenge.converters.CargoTypeConverter;
import me.approximations.javacodechallenge.enums.Cargo;

@Getter
@Setter
@EqualsAndHashCode(of="id")
@NoArgsConstructor(force=true)
@Entity
@Table(name="usuarios")
public class Usuario {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private final Long id;
    private String name;
    private String cpf;
    private String email;
    private String password;

    @Convert(converter=CargoTypeConverter.class)
    private Cargo role;

    public Usuario(Long id, String name, String cpf, String email, String password, Cargo role) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
