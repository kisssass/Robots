package model;

public interface Entity {
    double getEntityPositionX();
    double getEntityPositionY();
    int getEntitySize();
    void update(ModelContext modelContext);
}
