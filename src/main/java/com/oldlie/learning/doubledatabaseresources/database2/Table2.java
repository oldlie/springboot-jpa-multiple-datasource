package com.oldlie.learning.doubledatabaseresources.database2;

import lombok.*;

import javax.persistence.*;

/**
 * @author CL
 * @date 2020/10/20
 */
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "t_t22")
@ToString
public class Table2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "ta_text")
    private String taText;
}
