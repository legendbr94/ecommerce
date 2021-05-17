package com.pedro.ecommerce.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name= "nota_fiscal")
public class NotaFiscal extends EntidadeBaseInteger {

    @NotNull
    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "pedido_id",nullable = false,
            foreignKey = @ForeignKey(name = "fk_nota_fiscal_pedido"))
    //É possível utilizar o JoinTable no lugar de JoinColumn também.
    //    @JoinTable(name = "pedido_nota_fiscal",
    //            joinColumns = @JoinColumn(name = "nota_fiscal_id", unique = true),
    //            inverseJoinColumns = @JoinColumn(name = "pedido_id", unique = true))
    private Pedido pedido;

    @NotEmpty
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.BinaryType")
    @Lob
    private byte[] xml;

    @PastOrPresent
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_emissao",nullable = false)
    private Date dataEmissao;
}
