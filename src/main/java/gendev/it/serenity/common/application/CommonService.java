package gendev.it.serenity.common.application;

import gendev.it.serenity.common.repo.CommonRepository;

public class CommonService<T, ID, JPA extends CommonRepository<T, ID>> {
    private final JPA jpa;

    public CommonService(JPA jpa) {
        this.jpa = jpa;
    }

    public JPA getJpa() {
        return jpa;
    }

    public T save(T model) {
        return jpa.save(model);
    }

    public void delete(T model) {
        jpa.delete(model);
    }

   
}
