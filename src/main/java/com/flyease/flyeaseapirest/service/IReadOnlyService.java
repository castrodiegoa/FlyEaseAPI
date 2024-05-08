package com.flyease.flyeaseapirest.service;

import java.util.List;

public interface IReadOnlyService<T, ID> {

    List<T> listAll();

    T findById(ID id);

    boolean existsById(ID id);

}
