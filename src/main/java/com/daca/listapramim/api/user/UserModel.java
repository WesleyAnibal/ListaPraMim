package com.daca.listapramim.api.user;

import com.daca.listapramim.api.utils.Model;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@ApiIgnore
@Entity
@Table(name = "tb_user")
@EntityListeners({UserListener.class})
public class UserModel implements Serializable, Model<Long> {

    private static final long serialVersionUID = 2967515815580857179L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name="nome", nullable = false)
    private String nome;

    @NotEmpty
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(targetClass = Privilege.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_privilege", joinColumns = @JoinColumn(name = "user_id"), foreignKey = @ForeignKey(name = "FK_USER_PRIVILEGE"))
    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Privilege> privileges;


    public UserModel() {
    }

    public UserModel(String nome, String email, String password) {
        this.nome = nome;
        this.email = email;
        this.password = password;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(nome, userModel.nome) &&
                Objects.equals(email, userModel.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, email);
    }
}
