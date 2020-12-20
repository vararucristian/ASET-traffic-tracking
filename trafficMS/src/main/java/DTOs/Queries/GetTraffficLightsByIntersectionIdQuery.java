package DTOs.Queries;

public class GetTraffficLightsByIntersectionIdQuery {
    private int id;

    public GetTraffficLightsByIntersectionIdQuery(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
