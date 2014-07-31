package rxdemo;

import org.junit.Test;

import java.util.Arrays;

import static rxdemo.MovieDb.*;

public class MovieDBTest {
    @Test
    public void testGetMovies() throws Exception {
        MovieInfo[] movies = getMovies();
        System.out.println(Arrays.toString(movies));
    }

    @Test
    public void testGetCast() throws Exception {
        MovieInfo cowgirlBlues = new MovieInfo();
        cowgirlBlues.setId(264688);
        ActorInfo[] actors = getCast(cowgirlBlues);
        System.out.println(Arrays.toString(actors));
    }

    @Test
    public void testGetActorDetails() throws Exception {
        ActorInfo drakeHogestyn = new ActorInfo();
        drakeHogestyn.setId(9274);
        ActorDetails details = getActorDetails(drakeHogestyn);
        System.out.println(details);
    }
}
