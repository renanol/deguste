package br.com.deguste.model.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@SequenceGenerator(name = "seqVenda", sequenceName="seq_Venda", allocationSize = 1)
public class Venda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8457159111756621557L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seqVenda")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_pedido", referencedColumnName="id")
	private Pedido pedido;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_usuario", referencedColumnName="id")
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_cliente", referencedColumnName="id")
	private Cliente cliente;
	
	@Temporal(TemporalType.DATE)
	private Date dataVenda;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date horaVenda; 
	
	@Column
	private BigDecimal valorVenda;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Cliente getCliente() {
		if(this.cliente == null){
			this.cliente = new Cliente();
		}
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Date getHoraVenda() {
		return horaVenda;
	}

	public void setHoraVenda(Date horaVenda) {
		this.horaVenda = horaVenda;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

}
