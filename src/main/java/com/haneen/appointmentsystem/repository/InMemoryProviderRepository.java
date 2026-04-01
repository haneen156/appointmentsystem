package com.haneen.appointmentsystem.repository;

import com.haneen.appointmentsystem.domain.model.Provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryProviderRepository implements ProviderRepository{

    private Map<Long,Provider> storage = new HashMap<>();

    private Long idCounter = 1L;

    @Override
    public Optional<Provider> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Provider save(Provider provider) {
        if(provider.getId() == null){
            provider.setId(idCounter++);
        }
        storage.put(provider.getId(), provider);

        return provider;
    }
}
