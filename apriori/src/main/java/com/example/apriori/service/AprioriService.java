package com.example.apriori.service;

import java.util.Set;

public interface AprioriService {
    Set<String> getPossibleAssociation();
    void getSupportTable();
}
