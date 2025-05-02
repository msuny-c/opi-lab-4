package ru.itmo.app.checker;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import ru.itmo.app.bean.AttemptBackingBean;

@Default
@ApplicationScoped
public class AreaChecker implements IAreaChecker {
    @Override
    public boolean inArea(AttemptBackingBean attempt) {
        double x = attempt.getX();
        double y = attempt.getY();
        double r = attempt.getR();
        return inCircle(x, y, r) || inRectangle(x, y, r) || inTriangle(x, y, r);
    }
    private boolean inCircle(double x, double y, double r) {
        return Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r, 2) && x >= 0 && y <= 0;
    }
    private boolean inRectangle(double x, double y, double r) {
        return -r <= x && x <= 0 && y <= 0 && y >= -r / 2;
    }
    private boolean inTriangle(double x, double y, double r) {
        return x <= 0 && y >= 0 && x >= -r && y <= r && y <= x + r;
    }
}
