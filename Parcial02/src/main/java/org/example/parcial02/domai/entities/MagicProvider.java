package org.example.parcial02.domai.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "magic_provider")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MagicProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MagicType type;

    @Column(nullable = false)
    private Boolean active;
}
