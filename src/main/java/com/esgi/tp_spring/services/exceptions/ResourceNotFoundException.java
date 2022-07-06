package com.esgi.tp_spring.services.exceptions;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(Class<?> resourceType, Object resourceId) {
        super(resourceType.getSimpleName() + " " + resourceId + " not found.");
    }
}
