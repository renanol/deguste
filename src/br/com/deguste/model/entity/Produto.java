package br.com.deguste.model.entity;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;



@Entity
@SequenceGenerator (name ="seqProduto", sequenceName="seq_produto", allocationSize = 1)
public class Produto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2511348821988051956L;
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "seqProduto")
	private Long id;
	
	@Column (nullable = false, length = 50)
	private String nome;
	
	@Column (nullable = false, length = 100)
	private String codigo;
	
	
	@Column(nullable = false)
	private BigDecimal custo;
	
	@Column(nullable = false)
	private BigDecimal preco;
	
	@Column (columnDefinition = "TEXT")
	private String observacao;
	
	@Transient
	private BigDecimal lucro;
	
	@Column
	private int estoque;
	
	@Column
	private int estoqueMin;
	
	@Column
	private int estoqueMax;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_fornecedor", referencedColumnName="id")
	private Fornecedor fornecedor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_categoria", referencedColumnName="id")
	private Categoria categoria;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_unidadeMedida", referencedColumnName="id")
	private UnidadeMedida unidadeMedida;
	
	@Column
	private boolean ativo;
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj instanceof Produto)
			if(((Produto)obj).getId().equals(this.id)) return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
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
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public BigDecimal getCusto() {
		return custo;
	}
	public void setCusto(BigDecimal custo) {
		this.custo = custo;
	}

	public BigDecimal getLucro() {
		return lucro;
	}

	public void setLucro(BigDecimal lucro) {
		this.lucro = lucro;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public int getEstoqueMin() {
		return estoqueMin;
	}

	public void setEstoqueMin(int estoqueMin) {
		this.estoqueMin = estoqueMin;
	}

	public int getEstoqueMax() {
		return estoqueMax;
	}

	public void setEstoqueMax(int estoqueMax) {
		this.estoqueMax = estoqueMax;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	
	
}
