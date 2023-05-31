package com.jobpreparing;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> { //<T1, T2> T1 = type of the data that we will store, T2 = Type of ID which is customerID which is Integer.

}
