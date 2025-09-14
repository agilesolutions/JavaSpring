package com.agilesolutions.jpa.repository;

import com.agilesolutions.jpa.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
