import javax.persistence.EntityManager;

public class EmployeesWithSalaryBetween {
    private static final String GET_EMPLOYEES_WITH_SALARIES_BETWEEN = "select em from  employee em " +
            " where em.salary not between 30000 and 70000 " +
            " group BY em.department " +
            " order by em.salary desc";

    public static void main(String[] args) {
        EntityManager entityManager = dbConfig.getEntityManager();
        entityManager.getTransaction().begin();

        entityManager.createQuery(GET_EMPLOYEES_WITH_SALARIES_BETWEEN, String.class)
                .getResultList()
                .forEach(System.out::println);

        entityManager.getTransaction().commit();
        entityManager.close();

    }

}
