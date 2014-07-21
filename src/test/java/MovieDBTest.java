import org.junit.Test;

import java.util.Arrays;

public class MovieDBTest {
    @Test
    public void testGetMovies() throws Exception {
        MovieDb.MovieInfo[] movies = MovieDb.getMovies();
        System.out.println(Arrays.toString(movies));
    }

    @Test
    public void testGetCast() throws Exception {
        MovieDb.MovieInfo cowgirlBlues = new MovieDb.MovieInfo();
        cowgirlBlues.setId(264688);
        MovieDb.ActorInfo[] actors = MovieDb.getCast(cowgirlBlues);
        System.out.println(Arrays.toString(actors));
    }

    @Test
    public void testGetActorDetails() throws Exception {
        MovieDb.ActorInfo drakeHogestyn = new MovieDb.ActorInfo();
        drakeHogestyn.setId(9274);
        MovieDb.ActorDetails details = MovieDb.getActorDetails(drakeHogestyn);
        System.out.println(details);
    }
}
