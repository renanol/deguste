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

@Entity
@SequenceGenerator(name = "seqPedido", sequenceName="seq_pedido", allocationSize = 1)
public class Pedido implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9150385878867009380L;
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seqPedido")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_produto", referencedColumnName="id")
	private Produto produto;
	
	@Column
	private Integer quantidade;
	
	@Column
	private BigDecimal valorVenda;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}
	

}
