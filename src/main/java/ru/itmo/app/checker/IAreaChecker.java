package ru.itmo.app.checker;

import ru.itmo.app.bean.AttemptBackingBean;

public interface IAreaChecker {
    boolean inArea(AttemptBackingBean attempt);
}
