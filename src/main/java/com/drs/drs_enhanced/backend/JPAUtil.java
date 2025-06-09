package com.drs.drs_enhanced.backend;


import jakarta.persistence.*;

public class JPAUtil {
    private static final EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("DRS_ENHANCED_PU");
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public static boolean checkConnection() {
        try (EntityManager em = getEntityManager()) {
            em.getTransaction().begin();  
            em.getTransaction().rollback();
            System.out.println("Database connection successful.");
            return true;
        } catch (Exception e) {
            System.err.println("Database connection failed:");
            System.out.println("Error:" + e.getMessage());
            return false;
        }
    }

    public static void close() {
        emf.close();
    }
}
