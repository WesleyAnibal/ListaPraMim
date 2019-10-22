package com.daca.listapramim.api.item;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.daca.listapramim.api.compra.Compra;
import com.daca.listapramim.api.precos.MapaDePreco;
import com.daca.listapramim.api.user.UserModel;
import com.daca.listapramim.api.utils.Model;

import java.io.Serializable;
import java.util.List;

@Entity
@Inheritance
@Table(name = "tb_item",
		uniqueConstraints = @UniqueConstraint(columnNames={"nome", "categoria"}))
public abstract class Item implements Serializable, Model<Long>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(name = "nome", nullable = false)
	private String nome;

	@Enumerated(EnumType.STRING)
	@Column(name = "categoria", nullable = false)
	private Categoria categoria;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private List<MapaDePreco> precos;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private List<Compra> compras;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userModel_id", nullable = false)
	private UserModel userModel;



	public Item(String nome, Categoria categoria) {
		this.nome = nome;
		this.categoria = categoria;
	}

    public Item(Long id){
	    this.id = id;
    }

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public Item() {
	}

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public Categoria getCategoria() {
		return categoria;
	}



	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    public List<MapaDePreco> getPrecos() {
        return precos;
    }

    public void setPrecos(List<MapaDePreco> precos) {
        this.precos = precos;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (categoria != other.categoria)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Item";
	}
}
