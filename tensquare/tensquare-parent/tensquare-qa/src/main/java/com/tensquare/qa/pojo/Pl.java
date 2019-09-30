package com.tensquare.qa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.serializer.Serializer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_pl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pl implements Serializable {
    @Id
    private String problemid;//问题id
    @Id
    private String labelid;//标签id
}
