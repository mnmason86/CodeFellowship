package com.mnmason6.codefellowship.repositories;

import com.mnmason6.codefellowship.models.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
    SiteUser findByUsername(String username);
}
