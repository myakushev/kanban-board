package com.myakushev.api.requests;

public interface CrudInterface {
    public Object create(Object obj);

    public Object get(Integer id);

    public Object get(String title);

    public Object update(Integer id, Object obj);

    public Object delete(Integer id);
}
