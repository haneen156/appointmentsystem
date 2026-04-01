package com.haneen.appointmentsystem.repository;

import com.haneen.appointmentsystem.domain.model.Provider;

import java.util.Optional;

public interface ProviderRepository {
    Optional<Provider> findById(Long id);

    Provider save(Provider provider);
}
