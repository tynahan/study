package me.tynahan.demoinsttinfomanage.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class InsttInfo {

    @Id @GeneratedValue
    @Column(name = "instt_id")
    private Long id;

    private String name;

    private String code;

    private String ip;

    private String loginId;

    @Enumerated(STRING)
    private ConnectMode connectMode; // CONNECTABLE, NON_CONNECTABLE

    @Enumerated(STRING)
    private OnnaraDivision onnaraDivision; // ONNARA_1_0, ONNARA_2_0

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private InsttInfo parent;

    @OneToMany(mappedBy = "parent")
    private List<InsttInfo> child = new ArrayList<>();

    public void addChildInsttInfo(InsttInfo child) {
        this.child.add(child);
        child.setParent(this);
    }
}
