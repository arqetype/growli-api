package com.arqetype.growli.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "first_name")
    @Size(min = 3, max = 32, message = "First name must be between 3 and 32 characters long")
    @Pattern(regexp = "^\\D*$", message = "First name must not contain digits")
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 3, max = 32, message = "Last name must be between 3 and 32 characters long")
    @Pattern(regexp = "^\\D*$", message = "Last name must not contain digits")
    private String lastName;

    private byte[] avatar;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @UpdateTimestamp
    @Column(name= "updated_at", nullable = false)
    private Long updatedAt;
}
