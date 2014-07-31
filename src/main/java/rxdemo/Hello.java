package rxdemo;

import lombok.Data;
import rx.Observable;
import rx.functions.Func1;

import static rx.Observable.*;
import static rxdemo.MovieDb.*;

public class Hello {
    public static void main(String[] args) throws Exception {
        System.out.println("main.java.Hello");

        Observable<MovieWithAge> result = parallelMap(getMovies(),
                movie -> new MovieWithAge(movie.getTitle(), 55));
        result.doOnEach(System.out::println);
    }

    private static Observable<MovieInfo> getMovies()
            throws Exception {
        return from(MovieDb.getMovies());
    }

    private static <X, Y> Observable<Y> parallelMap(Observable<X> obs,
            Func1<X, Y> func) {
        return obs.parallel(item -> item.map(func));
    }

    @Data
    private static class MovieWithAge {
        private final String title;
        private final int age;
    }
}
