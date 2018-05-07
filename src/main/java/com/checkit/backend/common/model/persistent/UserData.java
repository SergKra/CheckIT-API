package com.checkit.backend.common.model.persistent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Created by Gleb Dovzhenko on 23.04.2018.
 */
@Entity
@Table(name = "user_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    public UserData(String registrationEmail) {
        this.registrationEmail = registrationEmail;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message="{validation.registrationEmail.NotEmpty.message}")
    @Size(min=1, max=60, message="{validation.registrationEmail.Size.message}")
    @Column(name = "email", unique = true)
    private String registrationEmail;

    @NotEmpty(message="{validation.first_name.NotEmpty.message}")
    @Size(min=1, max=60, message="{validation.first_name.Size.message}")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message="{validation.last_name.NotEmpty.message}")
    @Size(min=3, max=60, message="{validation.last_name.Size.message}")
    @Column(name = "last_name")
    private String lastName;

    @Size(min=3, max=60, message="{validation.phone.Size.message}")
    @Column(name = "phone")
    private String phone;

    @Column(name = "website")
    private String websiteURL;

    @NotEmpty(message="{validation.profile_url.NotEmpty.message}")
    @Size(min=1, max=90, message="{validation.profile_url.Size.message}")
    @Column(name = "profile_url", unique = true)
    private String profileURL;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Size(min=1, max=90, message="{validation.profile_photo.Size.message}")
    @Column(name = "profile_photo")
    private String profilePhotoURL;

    @Column(name = "about")
    private String about;

}
