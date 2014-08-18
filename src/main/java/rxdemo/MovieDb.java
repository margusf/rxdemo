package rxdemo;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Connection to movie database:
 * http://docs.themoviedb.apiary.io/
 */
public class MovieDb {
    private static final Gson GSON = new Gson();
    private static final DateFormat DATE_FORMAT =
            new SimpleDateFormat("y-M-d");
    private static long MSEC_IN_YEAR = 1000L * 60 * 60 * 24 * 365;

    @SneakyThrows
    public static MovieInfo[] getMovies() {
        System.out.println("getMovies()");
        // GEThttp://api.themoviedb.org/3/discover/movie
        String json = HttpHelper.doGet(
                "http://api.themoviedb.org/3/discover/movie?api_key=0835385142505861236af08685de3c1f");

        DiscoverResponse response = GSON.fromJson(
                json, DiscoverResponse.class);
        System.out.println("getMovies() -> " + response.getResults().length);
        return response.getResults();
    }

    @SneakyThrows
    public static ActorInfo[] getCast(MovieInfo movie) {
        String json = HttpHelper.doGet(
                        "http://api.themoviedb.org/3/movie/" + movie.getId()
                                + "/credits?api_key=0835385142505861236af08685de3c1f");
        CastResponse response = GSON.fromJson(json, CastResponse.class);
        return response.getCast();
    }

    @SneakyThrows
    public static ActorDetails getActorDetails(ActorInfo actor) {
        String json = HttpHelper.doGet(
                        "http://api.themoviedb.org/3/person/" + actor.getId()
                                + "?api_key=0835385142505861236af08685de3c1f");
        return GSON.fromJson(json, ActorDetails.class);
    }

    @Data
    public static class MovieInfo {
        private int id;
        private String title;
        @SerializedName("release_date")
        private String releaseDateString;

        @SneakyThrows
        public Date getReleaseDate() {
            try {
                return DATE_FORMAT.parse(releaseDateString);
            } catch (ParseException ex) {
                return null;
            }
        }
    }

    @Data
    public static class DiscoverResponse {
        private MovieInfo[] results;
    }

    @Data
    public static class CastResponse {
        private ActorInfo[] cast;
    }

    @Data
    public static class ActorInfo {
        private int id;
        private String name;
    }

    @ToString(callSuper=true)
    public static class ActorDetails extends ActorInfo {
        @SerializedName("birthday")
        @Setter private String birthdayString;

        @SneakyThrows
        public Date getBirthDate() {
//            System.out.println("getBirthDay(" + getName() + ", "
//                    + birthdayString + ")");
            try {
                return birthdayString == null
                        ? null : DATE_FORMAT.parse(birthdayString);
            } catch (ParseException ex) {
                return null;
            }
        }

        public Integer getAge(Date atDate) {
            Date birthDate = getBirthDate();
            if (birthDate == null) {
                return null;
            } else {
                return (int) ((atDate.getTime() - birthDate.getTime())
                        / MSEC_IN_YEAR);
            }
        }
    }
}
