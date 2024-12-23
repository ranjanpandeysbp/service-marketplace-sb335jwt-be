package com.mycompany.smp.service;

import java.util.List;

public interface CommonService <T,R>{
    public T add(R request);
    public T update(R request, Long id);
    public T delete(Long id);
    public T get(Long id);
    public List<T> getAll(Long id);
    public List<T> search(R request);
}
