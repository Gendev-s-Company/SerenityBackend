package gendev.it.serenity.users.domain.dto;

import java.time.LocalDate;

public class UserResponseDTO {
    private String userID;
    private String name;
    private ProfilDTO profil; 
    private String phone;
    private LocalDate joinedDate;
    private int status;

 

    public UserResponseDTO(String userID, String name, ProfilDTO profil, String phone, LocalDate joinedDate,
            int status) {
        this.userID = userID;
        this.name = name;
        this.profil = profil;
        this.phone = phone;
        this.joinedDate = joinedDate;
        this.status = status;
    }

    // ----- Getters -----
    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getStatus() {
        return status;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public ProfilDTO getProfil() {
        return profil;
    }

    // ----- Setters (optionnel, selon besoin) -----
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    public void setProfil(ProfilDTO profil) {
        this.profil = profil;
    }
}

