package com.kiosco.tenant_service.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table( name = "store", schema = "dbo")
public class Store extends Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "default_language", length = 10)
    private String defaultLanguage;

    private String timezone;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_info", nullable = false)
    private String ownerInfo;

    @Column(name = "info", nullable = false, columnDefinition = "TEXT")
    private String info;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Branch> branches;

    public void addBranch(Branch branch) {
        branch.setStore(this);
        this.branches.add(branch);
    }
}
