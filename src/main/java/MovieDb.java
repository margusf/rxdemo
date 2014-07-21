import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Connection to movie database:
 * http://docs.themoviedb.apiary.io/
 */
public class MovieDb {
    private static final Gson GSON = new Gson();

    static MovieInfo[] getMovies() throws Exception {
        // GEThttp://api.themoviedb.org/3/discover/movie
        String json = HttpHelper.doGet(
                "http://api.themoviedb.org/3/discover/movie?api_key=0835385142505861236af08685de3c1f");

        DiscoverResponse response = GSON.fromJson(
                json, DiscoverResponse.class);
        return response.getResults();
    }

    static ActorInfo[] getCast(MovieInfo movie) throws Exception {
        String json = HttpHelper.doGet(
                        "http://api.themoviedb.org/3/movie/" + movie.getId()
                                + "/credits?api_key=0835385142505861236af08685de3c1f");
        CastResponse response = GSON.fromJson(json, CastResponse.class);
        return response.getCast();
    }

    static ActorDetails getActorDetails(ActorInfo actor) throws Exception {
        String json = HttpHelper.doGet(
                        "http://api.themoviedb.org/3/person/" + actor.getId()
                                + "?api_key=0835385142505861236af08685de3c1f");
        ActorDetails response = GSON.fromJson(json, ActorDetails.class);
        return response;
    }

    @Data
    static class MovieInfo {
        private int id;
        private String title;
        @SerializedName("release_date")
        private String releaseDate;
    }

    @Data
    static class DiscoverResponse {
        private MovieInfo[] results;
    }

    @Data
    static class CastResponse {
        private ActorInfo[] cast;
    }

    @Data
    static class ActorInfo {
        private int id;
        private String name;
    }

    @ToString(callSuper=true)
    static class ActorDetails extends ActorInfo {
        @Getter @Setter private String birthday;
    }
}
