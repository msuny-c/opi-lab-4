package ru.itmo.app.bean;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ru.itmo.app.entity.AttemptEntity;
import ru.itmo.app.service.IAttemptService;

@Named
@ApplicationScoped
public class AttemptManagedBean implements Serializable {
    @Inject
    private IAttemptService service;

    public void save(AttemptBackingBean attempt) {
        service.save(attempt);
    }

    public List<AttemptEntity> getAll() {
        return service.getAll();
    }

    public void saveFromParams() {
        var params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        var attempt = new AttemptBackingBean();
        attempt.setX(Double.valueOf(params.get("x")));
        attempt.setY(Double.valueOf(params.get("y")));
        attempt.setR(Double.valueOf(params.get("r")));
        attempt.setType(params.get("type"));
        save(attempt);
    }
}
