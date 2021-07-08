package com.uniamerica.unijobsbackend.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String email;
    @NotNull
    private String senha;
    @NotNull
    @Column(nullable = false)
    private String nome;
    @NotNull
    private String celular;
    @NotNull
    private  String ra;

    @OneToOne
    private TipoUsuario tipoUsuario;

    public Usuario(Usuario user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.senha = user.getSenha();
        this.nome = user.getNome();
        this.celular = user.getNome();
        this.ra = user.getRa();
        this.tipoUsuario = user.getTipoUsuario();
    }

    public Usuario(Integer id_usuario) {
        id = id_usuario;
    }
}
