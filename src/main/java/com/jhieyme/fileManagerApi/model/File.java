package com.jhieyme.fileManagerApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String name;

    @Column
    private String type;

    @Lob //Large object
    @Column(length = 52428800)
    private byte[] data;
}