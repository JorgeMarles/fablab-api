package com.example.demo.services;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.example.demo.entities.BaseEntity;
import com.example.demo.repositories.GenericRepository;

@Service
public class GenericEntityService {

    @Autowired
    private ApplicationContext context;

    Logger logger = LoggerFactory.getLogger(GenericEntityService.class);

    private final Map<String, Class<? extends BaseEntity>> entityTypeMap = new HashMap<>();

    public GenericEntityService() {
        
    }

    @SuppressWarnings("unchecked")
    public BaseEntity createEntity(String entityType, Map<String, Object> data) {
        Class<? extends BaseEntity> entityClass = entityTypeMap.get(entityType);
        if (entityClass == null) {
            throw new IllegalArgumentException("Tipo de entidad no soportado: " + entityType);
        }

        try {
            BaseEntity entity = entityClass.getDeclaredConstructor().newInstance();

            if (data.containsKey("nombre")) {
                entity.setNombre((String) data.get("nombre"));
            }

            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (!entry.getKey().equals("id") && !entry.getKey().equals("nombre")) {
                    try {
                        entityClass.getMethod("set" + capitalize(entry.getKey()))
                                .invoke(entity, entry.getValue().toString());
                    } catch (NoSuchMethodException e) {
                        logger.error("No se encuenta el campo "+entry.getKey()+" en la entidad "+entityClass.getSimpleName(), e);
                    }
                }
            }

            // Obtiene el repositorio apropiado y guarda la entidad
            String repositoryName = entityClass.getSimpleName() + "Repository";
            repositoryName = Character.toLowerCase(repositoryName.charAt(0)) + repositoryName.substring(1);

            GenericRepository<BaseEntity> repository = (GenericRepository<BaseEntity>) context.getBean(repositoryName);

            return repository.save(entity);

        } catch (Exception e) {
            throw new RuntimeException("Error al crear entidad: " + e.getMessage(), e);
        }
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}