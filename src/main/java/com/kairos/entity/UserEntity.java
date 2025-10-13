package com.kairos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

/**
 * @author kaijiang
 * @date 2025/10/13 10:22
 * @description 用户实例
 */
@Entity
@Table(name = "kairos_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @NotBlank
    @Email
    @Column(name = "email", columnDefinition = "varchar(255)")
    @Comment(value = "邮箱")
    private String email;

    @NotBlank
    @Column(name = "password", columnDefinition = "varchar(32)")
    @Comment(value = "密码")
    private String password; // BCrypt 加密后存储

    @Column(name = "user_name", columnDefinition = "varchar(32)")
    @Comment(value = "用户名")
    private String username;

}
