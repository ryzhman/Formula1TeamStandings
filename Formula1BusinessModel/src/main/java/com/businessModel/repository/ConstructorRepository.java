package com.businessModel.repository;

import com.businessModel.model.Constructor;
import org.apache.tomcat.util.bcel.Const;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
public interface ConstructorRepository {
    void saveOrUpdate(Constructor constructor);

    Constructor getConstructorByTitle(String title) throws IOException;

    void removeAllStandings();

    Collection<Constructor> getStandings();
}
