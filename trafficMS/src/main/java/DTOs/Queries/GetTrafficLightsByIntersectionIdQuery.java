package DTOs.Queries;

public class GetTrafficLightsByIntersectionIdQuery {
    private int id;

    public GetTrafficLightsByIntersectionIdQuery(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
