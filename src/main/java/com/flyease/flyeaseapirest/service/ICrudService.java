package com.flyease.flyeaseapirest.service;

public interface ICrudService<T, D, ID> extends IReadOnlyService<T, ID> {

    T save(D dto);

    T update(D dto);

    void delete(T entity);

}
