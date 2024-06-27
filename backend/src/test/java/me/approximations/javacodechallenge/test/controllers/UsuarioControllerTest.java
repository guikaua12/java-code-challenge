package me.approximations.javacodechallenge.test.controllers;

import me.approximations.javacodechallenge.entities.Departamento;
import me.approximations.javacodechallenge.entities.Usuario;
import me.approximations.javacodechallenge.enums.Cargo;
import me.approximations.javacodechallenge.handler.enums.ErrorEnum;
import me.approximations.javacodechallenge.repositories.DepartamentoRepository;
import me.approximations.javacodechallenge.repositories.UsuarioRepository;
import me.approximations.javacodechallenge.security.CustomUserDetails;
import me.approximations.javacodechallenge.security.jwt.token.JwtAuthenticationToken;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControllerTest {
    private static final JwtAuthenticationToken ADMIN_AUTHENTICATION = new JwtAuthenticationToken(new CustomUserDetails(new Usuario(1L, "Admin", "634.534.427-33", "email", "password", Cargo.ADMIN)), true);
    private static final JwtAuthenticationToken MEMBER_AUTHENTICATION = new JwtAuthenticationToken(new CustomUserDetails(new Usuario(1L, "Member", "634.534.427-33", "email", "password", Cargo.MEMBER)), true);

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UsuarioRepository usuarioRepository;
    @MockBean
    private DepartamentoRepository departamentoRepository;

    /* login */
    @Test
    public void shouldLoginUser() throws Exception {
        Usuario user = new Usuario(1L, "Test", "12345678901", "test@test.com", "password", Cargo.ADMIN);

        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@test.com\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id", is(1)))
                .andExpect(jsonPath("$.user.email", is("test@test.com")))
                .andExpect(jsonPath("$.token.token").exists());
    }

    @Test
    public void shouldNotLoginUserIfWrongPassword() throws Exception {
        Usuario user = new Usuario(1L, "Test", "12345678901", "test@test.com", "password", Cargo.ADMIN);

        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@test.com\", \"password\":\"wrongpassword\"}"))
                .andExpect(status().isUnauthorized());
    }

    /* register */
    @Test
    public void shouldRegisterUser() throws Exception {
        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());
        when(usuarioRepository.save(Mockito.any(Usuario.class))).thenAnswer(invocation -> {
            Usuario user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });
        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(new Departamento(1L, "Department")));

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\", \"cpf\":\"634.534.427-33\", \"email\":\"test@test.com\", \"password\":\"password\", \"departmentId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("test@test.com")))
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void shouldNotRegisterUser() throws Exception {
        Usuario user = new Usuario(1L, "Test", "634.534.427-33", "test@test.com", "password", Cargo.ADMIN);
        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nome\", \"cpf\":\"634.534.427-33\", \"email\":\"test@test.com\", \"password\":\"password\", \"departmentId\":1}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error", is(ErrorEnum.EMAIL_ALREADY_EXISTS.getMessage())));
    }

    /* create */
    @Test
    @WithMockUser(roles="ADMIN")
    public void shouldCreateUser() throws Exception {
        Usuario user = new Usuario(1L, "Test", "634.534.427-33", "test@test.com", "password", Cargo.ADMIN);

        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());
        when(usuarioRepository.save(Mockito.any())).thenReturn(user);
        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(new Departamento()));

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\", \"cpf\":\"634.534.427-33\", \"email\":\"test@test.com\", \"password\":\"password\", \"role\":\"ADMIN\", \"departmentId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="MEMBER")
    public void shouldNotCreateUserIfNotAdmin() throws Exception {
        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\", \"cpf\":\"634.534.427-33\", \"email\":\"test@test.com\", \"password\":\"password\", \"role\":\"ADMIN\", \"departmentId\":1}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void shouldNotCreateUserEmailExists() throws Exception {
        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.of(new Usuario()));

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\", \"cpf\":\"634.534.427-33\", \"email\":\"test@test.com\", \"password\":\"password\", \"role\":\"ADMIN\", \"departmentId\":1}"))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void shouldNotCreateUserRoleNotFound() throws Exception {
        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\", \"cpf\":\"634.534.427-33\", \"email\":\"test@test.com\", \"password\":\"password\", \"role\":\"INVALID_ROLE\", \"departmentId\":1}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void shouldNotCreateUserDepartmentNotFound() throws Exception {
        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());
        when(departamentoRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\", \"cpf\":\"634.534.427-33\", \"email\":\"test@test.com\", \"password\":\"password\", \"role\":\"ADMIN\", \"departmentId\":1}"))
                .andExpect(status().isNotFound());
    }

    /* getAll */
    @Test
    public void shouldGetAllUsers() throws Exception {
        Usuario user1 = new Usuario(1L, "Test", "634.534.427-33", "test@test.com", "password", Cargo.MEMBER);
        Usuario user2 = new Usuario(2L, "Test", "634.534.427-33", "test2@test.com", "password", Cargo.MEMBER);

        Page<Usuario> page = new PageImpl<>(Arrays.asList(user1, user2));

        when(usuarioRepository.getAll(PageRequest.of(0, 20))).thenReturn(page);

        mockMvc.perform(get("/user/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[1].id", is(2)));
    }

    /* findById */
    @Test
    public void shouldFindUserById() throws Exception {
        Usuario user = new Usuario(1L, "Test", "634.534.427-33", "test@test.com", "password", Cargo.MEMBER);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.email", is("test@test.com")))
                .andExpect(jsonPath("$.cpf", is("634.534.427-33")))
                .andExpect(jsonPath("$.role", is("MEMBER")));
    }

    @Test
    public void shouldNotFindUserById() throws Exception {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/user/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is(ErrorEnum.USER_NOT_FOUND.getMessage())));
    }

    /* update */

    @Test
    public void shouldUpdateSelfIfMember() throws Exception {
        Usuario user = new Usuario(1L, "Test", "634.534.427-33", "test@test.com", "password", Cargo.MEMBER);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(user));
        when(usuarioRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        SecurityContextHolder.getContext().setAuthentication(MEMBER_AUTHENTICATION);

        mockMvc.perform(patch("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\":\"Changed name\", \"cpf\":\"446.212.358-18\", \"email\":\"changed_email@test.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Changed name")))
                .andExpect(jsonPath("$.email", is("changed_email@test.com")))
                .andExpect(jsonPath("$.cpf", is("446.212.358-18")));
    }

    @Test
    public void shouldNotUpdateOtherUsersIfMember() throws Exception {
        Usuario user = new Usuario(2L, "Test", "634.534.427-33", "test@test.com", "password", Cargo.MEMBER);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(user));

        SecurityContextHolder.getContext().setAuthentication(MEMBER_AUTHENTICATION);

        mockMvc.perform(patch("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":2, \"name\":\"Changed name\", \"cpf\":\"446.212.358-18\", \"email\":\"changed_email@test.com\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldUpdateOtherUsersIfAdmin() throws Exception {
        Usuario user = new Usuario(2L, "Test", "634.534.427-33", "test@test.com", "password", Cargo.MEMBER);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(user));
        when(usuarioRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        SecurityContextHolder.getContext().setAuthentication(ADMIN_AUTHENTICATION);

        mockMvc.perform(patch("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":2, \"name\":\"Changed name\", \"cpf\":\"446.212.358-18\", \"email\":\"changed_email@test.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Changed name")))
                .andExpect(jsonPath("$.email", is("changed_email@test.com")))
                .andExpect(jsonPath("$.cpf", is("446.212.358-18")));
    }

    /* update password */
    @Test
    public void shouldUpdatePasswordSelfIfMember() throws Exception {
        final AtomicReference<String> savedPassword = new AtomicReference<>();

        Usuario user = new Usuario(1L, "Test", "634.534.427-33", "test@test.com", "password", Cargo.MEMBER);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(user));
        when(usuarioRepository.save(Mockito.any())).thenAnswer(invocation -> {
            final Usuario arg = invocation.getArgument(0);

            savedPassword.set(arg.getPassword());
            return arg;
        });

        SecurityContextHolder.getContext().setAuthentication(MEMBER_AUTHENTICATION);

        mockMvc.perform(patch("/user/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"password\":\"changed_password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        assertEquals("changed_password", savedPassword.get());
    }

    @Test
    public void shouldNotUpdateOtherUsersPasswordIfMember() throws Exception {
        Usuario user = new Usuario(2L, "Test", "634.534.427-33", "test@test.com", "password", Cargo.MEMBER);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(user));

        SecurityContextHolder.getContext().setAuthentication(MEMBER_AUTHENTICATION);

        mockMvc.perform(patch("/user/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":2, \"password\":\"changed_password\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldUpdateOtherUsersPasswordIfAdmin() throws Exception {
        final AtomicReference<String> savedPassword = new AtomicReference<>();

        Usuario user = new Usuario(2L, "Test", "634.534.427-33", "test@test.com", "password", Cargo.MEMBER);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(user));
        when(usuarioRepository.save(Mockito.any())).thenAnswer(invocation -> {
            final Usuario arg = invocation.getArgument(0);

            savedPassword.set(arg.getPassword());
            return arg;
        });

        SecurityContextHolder.getContext().setAuthentication(ADMIN_AUTHENTICATION);

        mockMvc.perform(patch("/user/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":2, \"password\":\"changed_password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)));

        assertEquals("changed_password", savedPassword.get());
    }

    /* delete */
    @Test
    @WithMockUser(roles="ADMIN")
    public void shouldDelete() throws Exception {
        Usuario user = new Usuario(2L, "Test", "634.534.427-33", "test@test.com", "password", Cargo.MEMBER);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(user));

        mockMvc.perform(delete("/user/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(usuarioRepository, times(1)).delete(user);
    }

}
