package me.approximations.javacodechallenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.approximations.javacodechallenge.enums.Cargo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String name;
    private String cpf;
    private String email;
    private Cargo role;
}
